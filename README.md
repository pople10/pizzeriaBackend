![alt text](https://github.com/pople10/pizzeriaBackend/blob/master/diagram.png?raw=true);
<hr>
<h1>Database as SQL commands</h1>

```sql
/* Database name ="pizzaria" */
CREATE TABLE `user`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `firstname` VARCHAR(255) NOT NULL,
    `lastname` VARCHAR(255) NOT NULL,
    `email` VARCHAR(120) UNIQUE NOT NULL,
    `status` VARCHAR(255) NOT NULL,
	`password` VARCHAR(1024) NOT NULL,
    `role` VARCHAR(255) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL
) ENGINE=InnoDB;
CREATE TABLE `token`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `token` VARCHAR(255) NOT NULL,
	`user` INT UNSIGNED NOT NULL,
	`expiration` DATETIME NOT NULL
) ENGINE=InnoDB;
CREATE TABLE `address`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `address1` VARCHAR(255) NOT NULL,
    `address2` VARCHAR(255) NOT NULL,
    `city` VARCHAR(255) NOT NULL,
    `zipcode` VARCHAR(255) NOT NULL,
    `country` VARCHAR(255) NOT NULL,
    `user` INT UNSIGNED NOT NULL
)ENGINE=InnoDB;
CREATE TABLE `orders`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `coupon` VARCHAR(125),
    `total` DOUBLE(8, 2) NOT NULL,
    `paid` DOUBLE(8, 2) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
	`status` VARCHAR(255) NOT NULL,
	`address` INT UNSIGNED NOT NULL,
    `delivery` INT UNSIGNED,
    `product` INT UNSIGNED NOT NULL,
    `wanted_at` DATETIME NOT NULL,
    `created_at` DATETIME NOT NULL,
	`updated_at` DATETIME NOT NULL
)ENGINE=InnoDB;
CREATE TABLE `coupon`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	`code` VARCHAR(125) NOT NULL UNIQUE,
    `amount` DOUBLE(8, 2) NOT NULL,
    `type` VARCHAR(255) NOT NULL,
    `expiration` DATETIME NULL
)ENGINE=InnoDB;
CREATE TABLE `products`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `description` TEXT NOT NULL,
	`photo` BLOB NOT NULL,
    `price` DOUBLE(8, 2) NOT NULL,
    `preparation_time_in_min` INT NOT NULL,
    `availability` TINYINT(1) NOT NULL
)ENGINE=InnoDB;
CREATE TABLE `log`(
    `id` INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    `ip` VARCHAR(255) NOT NULL,
    `user` INT UNSIGNED NOT NULL
)ENGINE=InnoDB;
ALTER TABLE
    `address` ADD CONSTRAINT `address_user_foreign` FOREIGN KEY(`user`) REFERENCES `user`(`id`);
ALTER TABLE
    `token` ADD CONSTRAINT `token_user_foreign` FOREIGN KEY(`user`) REFERENCES `user`(`id`);
ALTER TABLE
    `orders` ADD CONSTRAINT `orders_coupon_foreign` FOREIGN KEY(`coupon`) REFERENCES `coupon`(`code`);
ALTER TABLE
    `orders` ADD CONSTRAINT `orders_delivery_foreign` FOREIGN KEY(`delivery`) REFERENCES `user`(`id`);
ALTER TABLE
    `orders` ADD CONSTRAINT `orders_product_foreign` FOREIGN KEY(`product`) REFERENCES `products`(`id`);
ALTER TABLE
    `orders` ADD CONSTRAINT `orders_address_foreign` FOREIGN KEY(`address`) REFERENCES `address`(`id`);
ALTER TABLE
    `log` ADD CONSTRAINT `log_user_foreign` FOREIGN KEY(`user`) REFERENCES `user`(`id`);
/* default admin has a password of '123456' */
INSERT INTO `user` (`id`, `firstname`, `lastname`, `email`, `status`, `password`, `role`, `created_at`, `updated_at`) 
	VALUES (NULL, 'Admin', 'Admin', 'admin@admin.com', 'ACTIVE', '$2a$12$Y/klX4Tx.dI7QnWQ6odgXeem8n82HnPGRrFtT5BaN9A8SOWSqraUq', 'ADMIN', '2022-03-28 20:07:44', '2022-03-28 20:07:44');
```
