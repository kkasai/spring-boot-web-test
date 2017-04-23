import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.rules.ExternalResource;

public class ExternalRule extends ExternalResource {
    private  TestDescription testDescription;
    private IDatabaseTester iDatabaseTester;
    private IDatabaseConnection iDatabaseConnection;
    private static String xmlPath = "datasets/fixtures/fixture.xml";

    ExternalRule(TestDescription description, IDatabaseTester iDatabaseTester, IDatabaseConnection iDatabaseConnection) throws Exception{
        this.testDescription = description;
        this.iDatabaseTester = iDatabaseTester;
        this.iDatabaseConnection = iDatabaseConnection;
    }

    @Override
    protected void before() throws Throwable {
        iDatabaseConnection.getConnection().createStatement().executeUpdate("DELETE FROM " + Application.TABLE_NAME);
        iDatabaseConnection.getConnection().createStatement().executeUpdate("ALTER TABLE " + Application.TABLE_NAME + " AUTO_INCREMENT = 1");

        DatabaseOperation.CLEAN_INSERT.execute(iDatabaseConnection, new XmlDataSet(getClass().getResourceAsStream(xmlPath)));
    }
}
