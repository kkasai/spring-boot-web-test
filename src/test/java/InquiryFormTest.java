import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import page.InquiryFormPage;

public class InquiryFormTest extends Application {
    private static String TABLE_NAME = "inquiry";
    private static String expectedDataXmlPath = "datasets/testdata/sample.xml";
    private InquiryFormPage inquiryFormPage;


    @Rule
    public TestDescription testDescription = new TestDescription();
    @Rule
    public ExternalRule externalRule = new ExternalRule(testDescription, iDatabaseTester, iDatabaseConnection);

    public InquiryFormTest() throws Exception {
    }

    @Before
    public void setUp() {
        inquiryFormPage = PageFactory.initElements(testDescription.driver, InquiryFormPage.class);
        System.out.println("InquiryForm setup");
    }

    @Test
    public void inquiryFormTest() throws Exception {
        System.out.println("test1");
        testDescription.driver.get("http://localhost:8080/form");

        inquiryFormPage.nameTextBox.sendKeys("name");
        inquiryFormPage.emailTextBox.sendKeys("sample@sample.com");
        inquiryFormPage.contentTextarea.sendKeys("aaaaaaaaaaaa");
        inquiryFormPage.confirmButton.click();
        inquiryFormPage.submitButton.click();

        Assert.assertEquals("お問い合わせを受理しました。", inquiryFormPage.complateMessege.getText());

        IDataSet expected = new XmlDataSet(getClass().getResourceAsStream(expectedDataXmlPath));
        ITable expectedTable = expected.getTable(TABLE_NAME);
        IDataSet actual = iDatabaseTester.getConnection().createDataSet();
        ITable actualTable = actual.getTable(TABLE_NAME);

        Assertion.assertEquals(expected, actual);
        Assertion.assertEquals(expectedTable, actualTable);
    }
}
