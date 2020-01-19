package wantsome.presentation.admin_operations;

public class CheckPassword {
    private String password;

    public CheckPassword(String password) {
        this.password = password;
    }

    public String check() {
        return "<form method='post'>" +
                "<div style='background-color:#34ebcf; display:inline'>" +
                "  Enter password:<br>" +
                "<input type='password' name='password' value='" + password + "' required>" + "<br>" +
                "<input type='submit' value='Next'>" + "<br>" +
                "</form>";
    }
}
