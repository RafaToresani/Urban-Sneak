# Urban Sneak

Urban Sneak es un proyecto de eCommerce para una tienda de zapatos. El sistema cuenta con cuatro tipos de usuarios: Anon, Admin, Manager y Customer. Solo los usuarios Admin, Manager y Customer deben registrarse e iniciar sesión. Los usuarios Anon pueden acceder a los recursos públicos, como ver el listado de productos, ver la información detallada de un producto y consultar la cantidad de stock disponible de un producto.

Los usuarios de tipo Admin y Manager pueden dar de alta un nuevo producto, actualizar un producto y eliminar un producto. Además, pueden actualizar el stock de un producto existente en función de su color y talle. Cada usuario registrado tendrá asociado un carrito de compras. Dicho carrito almacenará productos. Cuando se almacene un producto en el carrito, se validará que haya stock disponible en el inventario. Si no se ha creado una orden de compra, el carrito se limpiará a las 72 horas de haber sido actualizado. Además, se podrá eliminar un producto del carrito y/o actualizar los productos que ya estén en el carrito, aumentando o disminuyendo la cantidad en el inventario. Los usuarios de tipo Customer podrán generar una orden de compra y visualizar su historial de órdenes.

### Análisis de requisitos funcionales:

*Roles: Admin, Manager, Customer.*

- RF01: El sistema debe permitir a cualquier usuario registrarse con el rol de **Customer**.

- RF02: El sistema debe permitir a los usuarios con el rol de **Customer** iniciar sesión.

- RF03: El sistema debe permitir al usuario con el rol de **Admin** otorgar el rol de **Manager** a un **Customer**.

- RF04: El sistema debe permitir a los usuarios con el rol de **Manager** iniciar sesión.

- RF05: El sistema debe permitir a los usuarios con el rol de **Admin** iniciar sesión.

- RF06: El sistema debe permitir a todos los usuarios visualizar el listado de productos disponibles.

- RF07: El sistema debe permitir a todos los usuarios visualizar los detalles de un producto específico.

- RF08: El sistema debe permitir a todos los usuarios verificar el stock disponible de un producto en función de su color y talle.

- RF09: El sistema debe permitir al **Admin** y a los **Managers** realizar operaciones de **Agregar**, **Borrar**, y **Modificar** (ABM) productos.

- RF10: El sistema debe permitir al **Admin** y a los **Managers** actualizar el stock de un producto en función de su color y talle.

- RF11: El sistema debe permitir la creación de un carrito de compras para cada usuario.

- RF12: El sistema debe permitir agregar un producto al carrito de compras, validando que la cantidad solicitada esté disponible en el inventario.

- RF13: El sistema debe reservar y modificar el stock disponible de un producto cuando este sea añadido a un carrito.

- RF14: El sistema debe permitir limpiar automáticamente el carrito después de 72 horas desde su última actualización.

- RF15: El sistema debe permitir modificar la cantidad de un producto en el carrito, validando que la cantidad solicitada esté disponible en el inventario.

- RF16: El sistema debe permitir eliminar un producto del inventario.

- RF17: El sistema debe permitir limpiar por completo el carrito de compras.

- RF18: El sistema debe permitir generar una orden de compra con el estado **Pendiente**, validando que los productos y sus cantidades estén disponibles en el inventario.

- RF19: El sistema debe permitir a los usuarios con el rol de **Customer** cancelar una orden de compra si no ha sido procesada por un **Admin**. La orden pasará al estado **Cancelado**.

- RF20: El sistema debe permitir a los usuarios con el rol de **Customer** visualizar su historial de órdenes de compra.

- RF21: El sistema debe permitir al **Admin** y a los **Managers** aprobar o rechazar una orden de compra.

- RF22: El sistema debe permitir al **Admin** y a los **Managers** modificar el estado de una orden de compra.

- RF23: El sistema debe notificar a los **Customers** cuando el estado de su orden de compra sea modificado.



## Diagrama UML
![Urban Sneak UML](resources/Urban-Sneak-UML.png)

