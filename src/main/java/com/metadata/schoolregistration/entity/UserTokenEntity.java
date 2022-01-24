package com.metadata.schoolregistration.entity;

import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "user_token")
@Data
public class UserTokenEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "ID", updatable = false, nullable = false)
  @Type(type="uuid-char")
  private UUID id;

  @NotNull(message = "Refresh token is required.")
  @Basic(optional = false)
  @Column(name = "refresh_token")
  private String refreshToken;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;
}
