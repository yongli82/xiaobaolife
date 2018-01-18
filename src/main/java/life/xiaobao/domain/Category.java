package life.xiaobao.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import life.xiaobao.domain.enumeration.RecordStatus;


/**
 * 类别
 */
@ApiModel(description = "类别")
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * code
     */
    @ApiModelProperty(value = "code")
    @Column(name = "code")
    private String code;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @Column(name = "name")
    private String name;

    /**
     * 小标题
     */
    @ApiModelProperty(value = "小标题")
    @Column(name = "description")
    private String description;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", coverImageBig='" + getCoverImageBig() + "'" +
            ", coverImageSmall='" + getCoverImageSmall() + "'" +
            ", memo='" + getMemo() + "'" +
            ", recordStatus='" + getRecordStatus() + "'" +
            "}";
    }
}
