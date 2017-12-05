package life.xiaobao.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import life.xiaobao.domain.enumeration.RecordStatus;


/**
 * 文章
 */
@ApiModel(description = "文章")
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * UUID
     */
    @ApiModelProperty(value = "UUID")
    @Column(name = "uuid")
    private String uuid;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @Column(name = "title")
    private String title;

    /**
     * 小标题
     */
    @ApiModelProperty(value = "小标题")
    @Column(name = "sub_title")
    private String subTitle;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    @Column(name = "author_name")
    private String authorName;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    @Column(name = "introduction")
    private String introduction;

    /**
     * 大封面
     */
    @ApiModelProperty(value = "大封面")
    @Column(name = "cover_image_big")
    private String coverImageBig;

    /**
     * 小封面
     */
    @ApiModelProperty(value = "小封面")
    @Column(name = "cover_image_small")
    private String coverImageSmall;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @Column(name = "content")
    private String content;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @Column(name = "publish_time")
    private ZonedDateTime publishTime;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Column(name = "memo")
    private String memo;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "record_status")
    private RecordStatus recordStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCoverImageBig() {
        return coverImageBig;
    }

    public void setCoverImageBig(String coverImageBig) {
        this.coverImageBig = coverImageBig;
    }

    public String getCoverImageSmall() {
        return coverImageSmall;
    }

    public void setCoverImageSmall(String coverImageSmall) {
        this.coverImageSmall = coverImageSmall;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(ZonedDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Article article = (Article) o;
        if (article.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", title='" + getTitle() + "'" +
            ", subTitle='" + getSubTitle() + "'" +
            ", authorName='" + getAuthorName() + "'" +
            ", introduction='" + getIntroduction() + "'" +
            ", coverImageBig='" + getCoverImageBig() + "'" +
            ", coverImageSmall='" + getCoverImageSmall() + "'" +
            ", content='" + getContent() + "'" +
            ", publishTime='" + getPublishTime() + "'" +
            ", memo='" + getMemo() + "'" +
            ", recordStatus='" + getRecordStatus() + "'" +
            "}";
    }
}
