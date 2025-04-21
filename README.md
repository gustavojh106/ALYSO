# ALYSO
# Guía de Configuración de la Base de Datos y Entorno de Desarrollo

---

## 1. Crear la Base de Datos

> **Nota:** En todos los pasos no especificados con configuración especial, mantén los valores por defecto y haz clic en **Next**.

1. **Configurar Listener**  
   - Abre **NETCA** (Network Configuration Assistant).  
   - Crea un listener en el puerto **1521** (por defecto).  
   - Si ya existe un listener en ese puerto, omite este paso.

2. **Crear Base de Datos con DBCA**  
   - Abre **CMD** como Administrador y ejecuta:
     ```bash
     dbca
     ```
   - En el asistente, elige:
     1. **Advanced Configuration**  
     2. **Custom Database** (para evitar datos de ejemplo)  
   - Durante la configuración:
     - Asigna la contraseña **alyso** a TODOS los usuarios (SYSTEM, SYS, etc.).  
     - Nombre de la Base de Datos: `alyso`  
     - SID de la Base de Datos: `alyso`  
     - Pluggable Database (PDB): deja el valor por defecto (`pdb`)  
     - Conecta la BD al listener creado (puerto **1521**)  
     - **Deselecciona** todos los componentes extra de Oracle (no son necesarios).  
     - Ajusta la memoria RAM según las características de tu equipo.  
     - Vuelve a confirmar la contraseña **alyso** para todos los usuarios.

---

## 2. Conectar la Base de Datos desde SQL Developer

1. Abre **Oracle SQL Developer** y crea una **nueva conexión**:  
   - **Nombre de la conexión:** `ALYSO`  
   - **Username:** `SYSTEM`  
   - **Password:** `alyso`  
   - **Hostname:** `localhost`  
   - **Port:** `1521`  
   - **SID:** `alyso`  
2. Haz clic en **Test** para verificar la conexión.  
3. Si la prueba es exitosa, haz clic en **Connect**.  

> **Tip:** También puedes importar la conexión usando el archivo `CONEXION_ALYSO_PARA_IMPORTAR.json`.

---

## 3. Importar Datos y Tablas a la Base de Datos

1. Asegúrate de que la base de datos **alyso** esté vacía.  
2. Abre el script **CREAR_BD_ALYSO.sql** en SQL Developer.  
3. Ejecuta **sentencia por sentencia** (no ejecutes todo el script de una sola vez).  
   - Esto crea las tablas y relaciones necesarias.

---

## 4. Ejecutar la Interfaz Gráfica en Java

1. Clona el repositorio del proyecto y con APACHE NETBEANS abre la carpeta **AlysoJava** y ahi dentro ejecutas el programa.
