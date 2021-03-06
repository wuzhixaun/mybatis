/**
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cursor.defaults;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetWrapper;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCursorTest {
  @Spy
  private ImpatientResultSet rs;
  @Mock
  protected ResultSetMetaData rsmd;
  @Mock
  private Connection conn;
  @Mock
  private DatabaseMetaData dbmd;
  @Mock
  private Statement stmt;

  @SuppressWarnings("unchecked")
//  @Test // add by 芋艿吗，临时去掉
  public void shouldCloseImmediatelyIfResultSetIsClosed() throws Exception {
    final MappedStatement ms = getNestedAndOrderedMappedStatement();
    final ResultMap rm = ms.getResultMaps().get(0);

    final Executor executor = null;
    final ParameterHandler parameterHandler = null;
    final ResultHandler<?> resultHandler = null;
    final BoundSql boundSql = null;
    final RowBounds rowBounds = RowBounds.DEFAULT;

    final DefaultResultSetHandler resultSetHandler = new DefaultResultSetHandler(executor, ms, parameterHandler,
      resultHandler, boundSql, rowBounds);

    when(stmt.getResultSet()).thenReturn(rs);
    when(rsmd.getColumnCount()).thenReturn(2);
    when(rsmd.getColumnLabel(1)).thenReturn("id");
    when(rsmd.getColumnType(1)).thenReturn(Types.INTEGER);
    when(rsmd.getColumnClassName(1)).thenReturn(Integer.class.getCanonicalName());
    when(rsmd.getColumnLabel(2)).thenReturn("role");
    when(rsmd.getColumnType(2)).thenReturn(Types.VARCHAR);
    when(rsmd.getColumnClassName(2)).thenReturn(String.class.getCanonicalName());
    when(stmt.getConnection()).thenReturn(conn);
    when(conn.getMetaData()).thenReturn(dbmd);
    when(dbmd.supportsMultipleResultSets()).thenReturn(false);

    final ResultSetWrapper rsw = new ResultSetWrapper(rs, ms.getConfiguration());

    try (DefaultCursor<?> cursor = new DefaultCursor<>(resultSetHandler, rm, rsw, RowBounds.DEFAULT)) {
      Iterator<?> iter = cursor.iterator();
      assertTrue(iter.hasNext());
      Map<String, Object> map = (Map<String, Object>) iter.next();
      assertEquals(Integer.valueOf(1), map.get("id"));
      assertEquals("CEO", ((Map<String, Object>) map.get("roles")).get("role"));

      assertFalse(cursor.isConsumed());
      assertTrue(cursor.isOpen());

      assertFalse(iter.hasNext());
      assertTrue(cursor.isConsumed());
      assertFalse(cursor.isOpen());
    }
  }

  @SuppressWarnings("serial")
  private MappedStatement getNestedAndOrderedMappedStatement() {
    final Configuration config = new Configuration();
    final TypeHandlerRegistry registry = config.getTypeHandlerRegistry();

    ResultMap nestedResultMap = new ResultMap.Builder(config, "roleMap", HashMap.class,
      new ArrayList<ResultMapping>() {
        {
          add(new ResultMapping.Builder(config, "role", "role", registry.getTypeHandler(String.class))
            .build());
        }
      }).build();
    config.addResultMap(nestedResultMap);

    return new MappedStatement.Builder(config, "selectPerson", new StaticSqlSource(config, "select person..."),
      SqlCommandType.SELECT).resultMaps(
        new ArrayList<ResultMap>() {
          {
            add(new ResultMap.Builder(config, "personMap", HashMap.class, new ArrayList<ResultMapping>() {
              {
                add(new ResultMapping.Builder(config, "id", "id", registry.getTypeHandler(Integer.class))
                  .build());
                add(new ResultMapping.Builder(config, "roles").nestedResultMapId("roleMap").build());
              }
            }).build());
          }
        })
        .resultOrdered(true)
        .build();
  }

  /*
   * Simulate a driver that closes ResultSet automatically when next() returns false (e.g. DB2).
   */
  protected abstract class ImpatientResultSet implements ResultSet {
    private int rowIndex = -1;
    private List<Map<String, Object>> rows = new ArrayList<>();

    protected ImpatientResultSet() {
      Map<String, Object> row = new HashMap<>();
      row.put("id", Integer.valueOf(1));
      row.put("role", "CEO");
      rows.add(row);
    }

    @Override
    public boolean next() throws SQLException {
      throwIfClosed();
      return ++rowIndex < rows.size();
    }

    @Override
    public boolean isClosed() throws SQLException {
      return rowIndex >= rows.size();
    }

    @Override
    public String getString(String columnLabel) throws SQLException {
      throwIfClosed();
      return (String) rows.get(rowIndex).get(columnLabel);
    }

    @Override
    public int getInt(String columnLabel) throws SQLException {
      throwIfClosed();
      return (Integer) rows.get(rowIndex).get(columnLabel);
    }

    @Override
    public boolean wasNull() throws SQLException {
      throwIfClosed();
      return false;
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
      return rsmd;
    }

    @Override
    public int getType() throws SQLException {
      throwIfClosed();
      return ResultSet.TYPE_FORWARD_ONLY;
    }

    private void throwIfClosed() throws SQLException {
      if (rowIndex >= rows.size()) {
        throw new SQLException("Invalid operation: result set is closed.");
      }
    }
  }
}
