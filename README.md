# Urban Sneak

Urban Sneak es un proyecto de eCommerce para una tienda de zapatos. El sistema cuenta con cuatro tipos de usuarios: Anon, Admin, Manager y Customer. Solo los usuarios Admin, Manager y Customer deben registrarse e iniciar sesión. Los usuarios Anon pueden acceder a los recursos públicos, como ver el listado de productos, ver la información detallada de un producto y consultar la cantidad de stock disponible de un producto.

Los usuarios de tipo Admin y Manager pueden dar de alta un nuevo producto, actualizar un producto y eliminar un producto. Además, pueden actualizar el stock de un producto existente en función de su color y talle. Cada usuario registrado tendrá asociado un carrito de compras. Dicho carrito almacenará productos. Cuando se almacene un producto en el carrito, se validará que haya stock disponible en el inventario. Si no se ha creado una orden de compra, el carrito se limpiará a las 72 horas de haber sido actualizado. Además, se podrá eliminar un producto del carrito y/o actualizar los productos que ya estén en el carrito, aumentando o disminuyendo la cantidad en el inventario. Los usuarios de tipo Customer podrán generar una orden de compra y visualizar su historial de órdenes.

## Análisis de requisitos funcionales

### Roles: *Admin, Manager, Customer.*

#### User Service.
- RF01: El sistema debe permitir a cualquier usuario registrarse como Customer. ✅
- RF02: El sistema debe crear automáticamente un carrito para cada usuario una vez que este se haya registrado. ✅
- RF02: El sistema debe permitir iniciar sesión a Customer, Admin o Manager. ✅
- RF03: El sistema debe permitir al Admin otorgar el rol de Manager a un Customer.
- RF04: El sistema debe permitir al Admin visualizar la lista de Managers.
- RF05: El sistema debe permitir al Admin y los Managers visualizar a los Customers.
- RF06: El sistema debe permitir al Admin y los Managers visualizar un Customer a detalle.

#### Product Service.
- RF07: El sistema debe permitir a TODOS los usuarios visualizar el listado de productos. ✅
- RF08: El sistema debe permitir a TODOS los usuarios visualizar un producto a detalle. ✅
- RF09: El sistema debe permitir a TODOS los usuarios visualizar el stock disponible de un producto, en función de su color y talle. ✅
- RF10: El sistema debe permitir al Admin y a los Managers crear un producto, con sus colores y sus talles. Además, debe inicializar un inventario para cada producto en base a su color y su talle. ✅
- RF11: El sistema debe permitir al Admin y a los Managers modificar el estado de un producto a inactivo y visceversa. ✅
- RF12: El sistema debe permitir al Admin y a los Managers modificar la información de un producto, exceptuando el skuCode, colores y talles. ✅
- RF13: El sistema debe permitir al Admin y a los Managers agregar talles a un producto e inicializarlos en el inventario.
- RF14: El sistema debe permitir al Admin y a los Managers agregar colores a un producto e inicializarlos en el inventario.

#### Inventory Service.
- RF15: El sistema debe permitir al Admin y a los Managers actualizar la cantidad en el inventario de un producto en función de su color y talle.
- RF16: El sistema debe permitir al Admin y a los Managers visualizar la totalidad de inventarios de un producto.

#### Cart Service.
- RF17: El sistema debe permitir agregar un item al carrito. Al agregarse, se debe validar que la cantidad esté disponible en el inventario, y se debe descontar dicha cantidad del inventario. ✅
- RF18: El sistema debe permitir modificar un la cantidad de item que ya esté en el carrito. La diferencia de dicha cantidad, sea que se haya agregado o restado, debe verse reflejada en el inventario del producto. ✅
- RF19: El sistema debe permitir eliminar un item del carrito. La cantidad de dicho item debe agregarse a el inventario del producto. ✅
- RF20: El sistema debe permitir limpiar el carrito. La cantidad de cada item del carrito debe agregarse a el inventario del producto. ✅
- RF21: El sistema debe permitir limpiar automáticamente el carrito después de 72 horas de haber sido actualizado. ✅
- RF22: El sistema debe permitir visualizar el carrito. ✅

#### Order Service.
- RF23: El sistema debe permitir generar una orden de compra con el estado "Pendiente".
- RF24: El sistema debe permitir a los Customers cancelar una orden de compra si no fue procesada por un Admin. Dicha orden pasará al estado CANCELLED.
- RF25: El sistema debe permitir a los Customers visualizar su historial de órdenes de compra.
- RF26: El sistema debe permitir al Admin y a los Managers aprobar o rechazar una orden de compra.
- RF27: El sistema debe permitir al Admin y a los Managers modificar el estado de una orden de compra.
- RF28: El sistema debe permitir notificar a los Customers cuando el estado de su orden ha sido modificado.


## Diagrama UML
![Urban Sneak UML](resources/Urban-Sneak-UML.png)

