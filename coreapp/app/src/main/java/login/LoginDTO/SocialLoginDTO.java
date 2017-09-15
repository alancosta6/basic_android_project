package login.LoginDTO;

/**
 * Created by alancosta on 9/14/17.
 */

public class SocialLoginDTO {

    private String socialNetworkLoginToken;
    private String socialNetworkUserID;
    private String socialNetworkUserEmail;
    private String socialNetworkUserName;
    private String socialNetworkPictureUrl;
    private Source socialLoginSource;
    private String gender;

    public enum Source {
        FACEBOOK,
        GOOGLE
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSocialNetworkUserID() {
        return socialNetworkUserID;
    }

    public void setSocialNetworkUserID(String socialNetworkUserID) {
        this.socialNetworkUserID = socialNetworkUserID;
    }

    public String getSocialNetworkUserEmail() {
        return socialNetworkUserEmail;
    }

    public void setSocialNetworkUserEmail(String socialNetworkUserEmail) {
        this.socialNetworkUserEmail = socialNetworkUserEmail;
    }

    public String getSocialNetworkUserName() {
        return socialNetworkUserName;
    }

    public void setSocialNetworkUserName(String socialNetworkUserName) {
        this.socialNetworkUserName = socialNetworkUserName;
    }

    public String getSocialNetworkPictureUrl() {
        return socialNetworkPictureUrl;
    }

    public void setSocialNetworkPictureUrl(String socialNetworkPictureUrl) {
        this.socialNetworkPictureUrl = socialNetworkPictureUrl;
    }

    public String getSocialNetworkLoginToken() {
        return socialNetworkLoginToken;
    }

    public void setSocialNetworkLoginToken(String socialNetworkLoginToken) {
        this.socialNetworkLoginToken = socialNetworkLoginToken;
    }

    public Source getSocialLoginSource() {
        return socialLoginSource;
    }

    public void setSocialLoginSource(Source socialLoginSource) {
        this.socialLoginSource = socialLoginSource;
    }

    @Override
    public String toString() {
        return "SocialLoginDTO{" +
                "socialNetworkLoginToken='" + socialNetworkLoginToken + '\'' +
                ", socialNetworkUserID='" + socialNetworkUserID + '\'' +
                ", socialNetworkUserEmail='" + socialNetworkUserEmail + '\'' +
                ", socialNetworkUserName='" + socialNetworkUserName + '\'' +
                ", socialNetworkPictureUrl='" + socialNetworkPictureUrl + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

}
