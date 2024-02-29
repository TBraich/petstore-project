package petstore.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Path Entity Jpa. */
@Entity
@Table(name = "app_path")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Path {
  @Id private String id;

  @Column(name = "method")
  private String method;

  @Column(name = "path", nullable = false)
  private String path;

  @Column(name = "description")
  private String description;

  @ManyToMany(mappedBy = "paths")
  private Set<Role> roles;
}
