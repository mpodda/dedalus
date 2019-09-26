package dedalus.dialect;

import org.hibernate.dialect.SQLServer2008Dialect;

import java.sql.Types;

/**
 * @see <a href="http://blog.tremend.ro/2007/05/">http://blog.tremend.ro/2007/05/</a>
 */
public class UnicodeSQLServer2008Dialect extends SQLServer2008Dialect {

    public UnicodeSQLServer2008Dialect() {
        super();

        // Use Unicode Characters
        registerColumnType(Types.VARCHAR, 255, "nvarchar($l)");
        registerColumnType(Types.CHAR, "nchar(1)");
        registerColumnType(Types.CLOB, "nvarchar(max)");
    }

}
