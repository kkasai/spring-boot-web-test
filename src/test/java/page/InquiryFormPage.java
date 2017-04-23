package page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InquiryFormPage {
    @FindBy(xpath = "//label[text()='名前']/following-sibling::div/input")
    public WebElement nameTextBox;

    @FindBy(xpath = "//label[text()='メールアドレス']/following-sibling::div/input")
    public WebElement emailTextBox;

    @FindBy(xpath = "//label[text()='内容']/following-sibling::div/textarea")
    public WebElement contentTextarea;

    @FindBy(xpath = "//button[text()='送信']")
    public WebElement confirmButton;

    @FindBy(xpath = "//button[text()='送信']")
    public WebElement submitButton;

    @FindBy(xpath = "//*[@id=\"complete\"]/p[1]")
    public WebElement complateMessege;

}
