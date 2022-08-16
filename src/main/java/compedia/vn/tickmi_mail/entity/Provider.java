package compedia.vn.tickmi_mail.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROVIDER")
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROVIDER_ID")
    private Long providerId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TOTAL_TICKET")
    private Integer totalTicket;

    @Column(name = "TOTAL_FOLLOWER")
    private Integer totalFollower;

    @Column(name = "TOTAL_FOLLOWING")
    private Integer totalFollowing;

    @Column(name = "CREATE_TIME")
    private Timestamp createTime;

    @Column(name = "REGISTER_PACKAGE_ID")
    private Long registerPackageId;

    @Column(name = "FACEBOOK_LINK")
    private String facebookLink;

    @Column(name = "INSTAGRAM_LINK")
    private String instagramLink;

    @Column(name = "LINKEDIN_LINK")
    private String linkedinLink;

    @Column(name = "TWITTER_LINK")
    private String twitterLink;

    @Column(name = "WEBSITE_LINK")
    private String websiteLink;

    @Column(name = "YOUTUBE_LINK")
    private String youtubeLink;
}
