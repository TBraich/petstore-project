CREATE TABLE IF NOT EXISTS app_user (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) DEFAULT NULL,
    email VARCHAR(150) NOT NULL,
    address TEXT DEFAULT NULL,
    birthday DATE DEFAULT NULL,
    phone VARCHAR(60) DEFAULT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(30) DEFAULT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_by VARCHAR(30) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS app_role (
    id VARCHAR(50) NOT NULL PRIMARY KEY, # Role Name
    description VARCHAR(250) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(30) DEFAULT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_by VARCHAR(30) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS app_path (
    id VARCHAR(50) NOT NULL PRIMARY KEY,
    method VARCHAR(10) NOT NULL,
    path VARCHAR(100) NOT NULL,
    description VARCHAR(250) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(30) DEFAULT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_by VARCHAR(30) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id VARCHAR(50) NOT NULL,
    role_id VARCHAR(10) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(30) DEFAULT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_by VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES app_user(id),
    FOREIGN KEY (role_id) REFERENCES app_role(id)
);

CREATE TABLE IF NOT EXISTS role_path (
    role_id VARCHAR(10) NOT NULL,
    path_id VARCHAR(50) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_by VARCHAR(30) DEFAULT NULL,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_by VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (path_id, role_id),
    FOREIGN KEY (path_id) REFERENCES app_path(id),
    FOREIGN KEY (role_id) REFERENCES app_role(id)
);