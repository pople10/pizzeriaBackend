
<p>Dans ce projet, on a essayé de simuler l'archeticture du Spring à partir de JEE native utilisant Jackson et Jersey dépendences (Mapper, Dao, GeneticDao, Beans, Controllers, Services...) avec <a _target="" href="https://github.com/pople10/pizzeriaFront">un Front-End</a> developpé par Angular</p>
<p>Le projet est à propos du développement d'une pizzeria en ligne</p>
<h2>Le site est basé à des fonctionalités comme les suivants : </h2>
<ul>
	<li>
		<h4>Gestion des utilisateur</h4>
		<ol>
			<li>Ajout d'un utilisateur (Seulement l'admin)</li>
			<li>Lister tous les utilisateurs (Seulement l'admin)</li>
			<li>Blocage d'un utilisateur (Seulement l'admin)</li>
			<li>Se connecter en générant un token qui va être valid au sein de 10 jours seulement</li>
			<li>Se déconnecter en suppirmant le token</li>
			<li>Registre pour les utilisateurs seuelement (User rôle seuelement)</li>
		</ol>
	</li>
	<li>
		<h4>Gestion des coupons</h4>
		<ol>
			<li>Ajout d'un coupon (Seulement l'admin)</li>
			<li>Lister tous les coupons (Seulement l'admin)</li>
			<li>Expiration d'un coupon (Seulement l'admin)</li>
			<li>Utilisation d'un coupon par l'utilisateur (si et seulement s'il n'est pas expiré)</li>
		</ol>
	</li>
	<li>
		<h4>Gestion des produits</h4>
		<ol>
			<li>Ajout d'un produit (Seulement l'admin)</li>
			<li>Lister tous les produits (Seulement l'admin)</li>
			<li>Cacher un produit (Seulement l'admin)</li>
			<li>Modification un produit (Seulement l'admin)</li>
			<li>Consultation d'un produit selon les rôles (Publique, Utilisateur, Livreur, Admin), chaqu'un est les données qui peut avoir</li>
			<li>Le seule admin qui peut consulter les produits cachés</li>
		</ol>
	</li>
	<li>
		<h4>Gestion des produits</h4>
		<ol>
			<li>Ajout d'un produit (Seulement l'admin)</li>
			<li>Lister tous les produits (Seulement l'admin)</li>
			<li>Cacher un produit (Seulement l'admin)</li>
			<li>Modification un produit (Seulement l'admin)</li>
			<li>Consultation d'un produit selon les rôles (Publique, Utilisateur, Livreur, Admin), chaqu'un est les données qui peut avoir</li>
			<li>Le seule admin qui peut consulter les produits cachés</li>
		</ol>
	</li>
	<li>
		<h4>Gestion des adresses</h4>
		<ol>
			<li>Ajout d'une adresse (Seulement l'utilisateur)</li>
			<li>Lister tous les adresses (Seulement l'utilisateur)</li>
		</ol>
	</li>
	<li>
		<h4>Gestion des commandes</h4>
		<ol>
			<li>Ajout d'un commande (Seulement l'utilisateur)</li>
			<li>Lister tous les commandes concernées:
				<ul>
					<li>tous pour l'admin</li>
					<li>les commandes possédées par l'utilisateur</li>
					<li>les commandes à livrées pour le livreur</li>
					<li>Sinon il ne peut pas les consulter dans le backend</li>
				</ul>
			</li>
			<li>Consulter une commandes (les données ne sont pas disponible pour tous les rôles)</li>
			<li>Changer le status une commandes (Seulement l'admin), mais s'il est COMPLETED, il ne peut pas</li>
			<li>Affecter un livreur à une commandes une seule fois seulement [à fin d'éviter le conflit) (Seulement l'admin)</li>
			<li>Consultation d'un produit selon les rôles (Publique, Utilisateur, Livreur, Admin), chaqu'un est les données qui peut avoir</li>
			<li>Le livreur peut marquer une commandes OUT FOR DELIVERY ou bien DELIVERED</li>
		</ol>
	</li>
	<li>
		<h4>Logging</h4>
		<ol>
			<li>Toute action effectuée par un utilisateur doit être enregisté pour des raisons de sécurité.</li>
			<li>L'enregistrement n'affecte pas la requête puisqu'on utilise un POST Interceptor</li>
		</ol>
	</li>
	<li>
		<h4>Gestion des rôles</h4>
		<p>On a implementé trois intercepteurs</p>
		<ol>
			<li>Un pour modifié le header afin d'envoyer les requêtes AJAX sur autres serveurs. (<a href="https://github.com/pople10/pizzeriaBackend/blob/master/src/main/java/com/project/pizzeria/interceptors/CORSFilter.java">CORPS</a>)</li>
			<li>Un pour valider la session (<a href="https://github.com/pople10/pizzeriaBackend/blob/master/src/main/java/com/project/pizzeria/interceptors/AuthentificationFilter.java">Authentication</a>)
				<ul>
					<li>Valider le token</li>
					<li>Voir l'éxpiration du token</li>
					<li>Voir si l'utilisateur est bloqué ou non</li>
					<li>Sinon la requête va être ecrasé avec un status du 401</li>
					<li>Dans le front on a <a href="https://github.com/pople10/pizzeriaFront/blob/master/src/app/interceptor/auth.interceptor.ts">un intercepteur</a> qui va appeler le service AuthService pour supprimer la session et diriger l'utilisateur pour se connécter à nouveau</li>
				</ul>
			</li>
			<li>Un pour valider les privillèges (<a href="https://github.com/pople10/pizzeriaBackend/blob/master/src/main/java/com/project/pizzeria/interceptors/AuthorizationFilter.java">Authorization</a>)
				<ul>
					<li>Valider le roles à partir l'URI souhaité</li>
					<li>Sinon la requête va être ecrasé avec un status du 403</li>
					<li>Dans le front on a <a href="https://github.com/pople10/pizzeriaFront/blob/master/src/app/guard/auth.guard.ts">un guard</a> qui s'appelle AuthGuard mais pas encore utilisé dans le routage puisque on a une validation dans le backend</li>
				</ul>
			</li>
		</ol>
	</li>
</ul>
<hr>
<p>Pour démarer l'application pour la première fois, on doit : </p>
<ul>
	<li>
		<h3>Pour backend :</h3>
		<ol type="1">
			<li>Avoir un tomcat du version 9.0</li>
			<li>Avoir un jdk du version 8.0 ou supérieur</li>
			<li>Maven et faire un Maven Clean Install dans IDE ou utilisant la commande:
			</li>
			
``` mvn clean install ```
		</ol>
		<h3>Pour <a _target="" href="https://github.com/pople10/pizzeriaFront">frontend</a> :</h3>
		<ol type="1">
			<li>Avoir un angular du version 9.0.0 ou supérieur</li>
			<li>NPM et faire un NPM Install dans IDE ou utilisant la commande:
``` npm install ```
			</li>
			<li>NG et faire un NG Serve ou NG Build dans IDE ou utilisant la commande:
``` ng  serve --open ```
			</li>
		</ol>
	</li>
</ul>
<h3 style="color:#ff0000;">N'oublier pas de modifier le lien du serveur dans le env.ts dans le <a _target="" href="https://github.com/pople10/pizzeriaFront">frontend</a> si vous souhaitez d'heberger l'application ou changer le port</h3>
<hr>

![alt text](https://github.com/pople10/pizzeriaBackend/blob/master/diagram.png?raw=true)
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
