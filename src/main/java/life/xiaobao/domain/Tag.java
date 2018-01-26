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
@Table(name = "mark_tag")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * uuid
     */
    @ApiModelProperty(value = "uuid")
    @Column(name = "uuid")
    private String uuid;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @Column(name = "name")
    private String name;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "record_status")
    private RecordStatus recordStatus = RecordStatus.VALID;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        Tag markTag = (Tag) o;
        if (markTag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), markTag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tag{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", name='" + getName() + "'" +
            ", recordStatus='" + getRecordStatus() + "'" +
            "}";
    }
}
