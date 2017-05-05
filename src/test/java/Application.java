import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.ClassRule;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

public class Application {
    private static String JDBC_URL = "jdbc:mysql://192.168.33.10/sample";
    private static String USER = "myapp";
    private static String PASSWORD = "myapp";
    public static String TABLE_NAME = "inquiry";

    static IDatabaseTester iDatabaseTester;
    static IDatabaseConnection iDatabaseConnection;
    private static Connection connection;

    Application() throws Exception{
    }

    @ClassRule
    public static TestRule dbRule = (statement, description) -> new Statement() {
        private File file;
        @Override
        public void evaluate() throws Throwable {
            dataBackUp();
            statement.evaluate();
            restore();
        }
        private void dataBackUp() throws Exception {
            System.out.println("TestRule databackup");
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            iDatabaseConnection = new DatabaseConnection(connection, connection.getSchema());
            iDatabaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            iDatabaseTester = new DefaultDatabaseTester(iDatabaseConnection);

            QueryDataSet partialDataSet = new QueryDataSet(iDatabaseConnection);
            partialDataSet.addTable(TABLE_NAME);
            file = File.createTempFile("prepare_backup",".xml");
            FlatXmlDataSet.write(partialDataSet, new FileOutputStream(file));
        }
        private void restore() throws Exception {
            System.out.println("TestRule restore");
            iDatabaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            iDatabaseTester.setDataSet(new FlatXmlDataSetBuilder().build(file));
            DatabaseOperation.CLEAN_INSERT.execute(iDatabaseConnection, new FlatXmlDataSetBuilder().build(file));
        }
    };
}
