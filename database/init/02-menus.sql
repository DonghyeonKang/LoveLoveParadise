ALTER TABLE families ADD COLUMN share_slug VARCHAR(255);
UPDATE families SET share_slug = id WHERE share_slug IS NULL;
ALTER TABLE families ALTER COLUMN share_slug SET NOT NULL;
ALTER TABLE families ADD CONSTRAINT uk_families_share_slug UNIQUE (share_slug);

CREATE TABLE menu_photos (
    id VARCHAR(255) NOT NULL,
    family_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT pk_menu_photos PRIMARY KEY (id)
);

CREATE INDEX idx_menu_photos_family_id ON menu_photos (family_id);

CREATE TABLE menus (
    id VARCHAR(255) NOT NULL,
    family_id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    photo_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    CONSTRAINT pk_menus PRIMARY KEY (id)
);

CREATE INDEX idx_menus_family_id ON menus (family_id);
