package instigatemobile.com.cookmaster.models;

public class ConsultantModel {
    private String mNameSurname;
    private String mEmail;
    private String mPhone;
    private String mImageUrl;

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public ConsultantModel(String mNameSurname, String mEmail, String mPhone, String mImageUrl) {
        this.mNameSurname = mNameSurname;
        this.mEmail = mEmail;
        this.mPhone = mPhone;
        this.mImageUrl = mImageUrl;
    }

    public ConsultantModel() {
    }

    public String getmNameSurname() {
        return mNameSurname;
    }

    public void setmNameSurname(String mNameSurname) {
        this.mNameSurname = mNameSurname;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}
