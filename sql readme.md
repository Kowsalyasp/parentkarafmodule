
# SQL MODULE

## TABLE OF CONTENTS

- [Abstract](#abstract)
- [Requirements](#requirements)
- [SQLDevListener](#sqldevlistener)
- [Database Configuration](#database-configuration)
- [MetaData](#metadata)
  * [*Table*](#table-tag)
    * [*Attributes of table tag*](#attributes-of-table-tag)
  * [*Column*](#column-tag)
    * [*Attributes of column tag*](#attributes-of-column-tag)
  * [*Primary-key*](#primary-key-tag)
    * [*Attributes of primary key tag*](#attributes-of-primary-key-tag)
  * [*Foreign-key*](#foreign-key-tag)
    * [*Attributes of foreign-key tag*](#attributes-of-foreign-key-tag)
  * [*Unique-key*](#unique-key-tag)
    * [*Attributes of unique-key tag*](#attributes-of-unique-key-tag)
  * [*Indexes*](#indexes-tag)
    * [*Attributes of indexes tag*](#attributes-of-indexes-tag)
  * [*meta.xml precedent*](#metaxml-precedent)
- [Data.xml](#dataxml)
  * [*data.xml file precedent*](#dataxml-file-precedent)
- [Packages](#packages)
  * [Api package](#api-package)
  * [Internal package](#internal-package)
- [Row Listener](#row-listener)
  * [Types of rowListener](#types-of-row-listener)
  * [RowListenerHandler](#rowlistenerhandler)
  * [RowListenerContainer](#rowlistenercontainer)


# Abstract
The SQL module Query implementations to perform DDL, DML, DQL actions for the tables in the database, Custom Queries against SQL database are implemented. In addition to this, Data store provisions (org, admin, readable, writable), Listener Actions(add, update and delete row), XmlToDCConverter and Sequence generator implementations are done in this module.

# Requirements
This module requires the following maven dependencies.
	
1. **javatuples -** A tuple is a collection of several elements that may or may not be related to each other. In other words, tuples can be considered anonymous objects.
2. **HikariCP -** HikariCP is a solid high-performance JDBC connection pool. A connection pool is a cache of database connections maintained so that the connections can be reused when future requests to the database are required.
3. **PostgreSQL -** PostgreSQL is an advanced, enterprise-class open-source relational database that supports both SQL (relational) and JSON (non-relational) querying.
	
# SQLDevListener
Utilizing the bundle lifecycle, it will perform actions like metadata parsing, etc. SQLDevListener explains the loading, processing, updating, and populating of the meta and data XML files. It notes the meta and data history record. We can see this in snapshots of our system. The MetaDataParser compares the existing XML file and the current XML file to update the data in the database. 
      
# Database Configuration File
In the karaf folder, need to create a folder named conf which should contain all the configuration files like database connectivity, cache server, and the webserver.

For database connection:
    create the `configuration source file` with a name `database.pgsql` with extension `cfg` 
(eg: database.pgsql.cfg)

A configuration file is a properties file containing key/value pairs:
          
 The cfg file provides a set of commands to manage the configuration.
> **property = value**

You can add your initial configuration directly in the file. To get the database service, mention the following mandatory properties in the given format.

Set the database name as you prefer and create the database with the same name in the database.
> **database.name =** TLC

To get the database server connection to mention the port number as follow: 
> **database.server =** localhost:5432

A default username and password that is connected to the database. 
> **database.username =** *****

> **database.password =** *****

Additionally, we can use the following properties to mention the pool size

Maximum pool size can be controlled using the below command.
This value will determine the maximum number of actual connections to the database backend.
> **connection.pool.max =** 

To acquire the Minimum number of idle connections to maintain in the pool.
> **connection.pool.idle =**

To make the connection idle for a certain period of time (in milliseconds.)
A connection will never be retired as idle before this timeout. A value of 0 means that idle connections are never removed from the pool.
> **connection.pool.idle.timeout =** 
	
To load meta and data xml files we need to set the developer mode true by mentioning the below line in custom.system.properties file in etc folder of karaf.
> **tlc.dev.mode** = true

# MetaData
The meta.xml file presents a set of metadata, such as the module name, and the tables to include. The meta.xml file to load and populate the metadata to fetch and configure the table definitions and initialize the sequence generation of primary keys. Meta.xml file performs all the DDL actions related to the table. We need to provide the table, columns and constraint details to create a table in the database. And the module tag refers to which module wants to create the table. Meta details of table should be described by below tags and attributes.

## Table tag
Create a table with the table name and type which are present inside the table tag. Each table has a table type value which can be given as per your needs.
``` 
<table name="TableName" type="11"> </table>
 ```

 ### *Attributes of table tag*

* **name =** Name of the table.
* **type =** Table Type creation is based on the existence of the following values partitionById, orgDependent, partitionByOrgId and commonNullExists. The following table types are as follows as
  * **1 -> COMMON:** If you don't specify any type, by default the table type is set to common. It does not depend on any ID.
  * **2 -> COMMON_PARTITIONED_BY_ID:** The partitionById is set true in COMMON table type definition to get this table.
  * **11 -> ORG_DEPENDENT:** The table is dependent on orgDependent alone.
  * **12 -> ORG_PARTITIONED_BY_ID:** This table type depends on orgDependent and partitionById.
  * **13 -> ORG_PARTITIONED_BY_ORG:** This table type depends on orgDependent and partitionByOrgId.
  * **21 -> ORG_MIXED:** This type of table depends on orgDependent and commonNullExists.
  * **22 -> ORG_MIXED_BY_ID:** This type of table depends on orgDependent, partitionById and commonNullExists.
  * **23 -> ORG_MIXED_BY_ORG:** This type of table depends on orgDependent, partitionByOrgId and commonNullExists.
    
## Column tag
 The <columns> tag consists of one or more <column> tags. Attributes of <column> tag include the column's name, data type, maximum length, nullable value that represents in boolean type, and the default value.

```
<columns>
    <column name="NAME" data-type="CHAR" max-length="30" nullable="false" default-value="1"/>
</columns>
```
 ### *Attributes of column tag*

* **name =** It refers to the column name.
* **data-type =** It refers to what type of column it is, and here we can use some specific data types.

   * **supported datatypes:**

     `BIGINT`: A large integer and it refers to the long data type.

     `INTEGER`: A medium integer it equals to the size of an int datatype.

     `SMALLINT`: A small integer and it equals to the short datatype.

     `DECIMAL`: An exact fixed-point number.

     `KCHAR`: Refers to an i18n datatype.The size parameter specifies the column length in characters - can be from 0 to 512. 

     `SCHAR`: Refers to the variable length string. 

     `BLOB`: For Binary Large Object(BLOB) holds the bytes of data.

     `CHAR`: A string can contain letters, numbers, and special characters. The size parameter specifies the column length in characters - can be from 0 to 255. 

     `TEXT`: A string can contain letters, numbers, and special characters. And it holds with a maximum length of 2500.

     `STEXT`: A string that holds the maximum length of 255 characters.

     `BOOLEAN`: A boolean is an expression that evaluates to either true or false.
  
* **nullable =** If we want to allow null values for the specific column, we can set nullable = true; otherwise, nullable = false.
* **max-length =** It refers to the maximum length that a column should be provided.
* **default-value =** The default value is assigned to the column. It may be a boolean type.

To modify the structure of existing tables in the database by adding, modifying, renaming, or dropping columns and constraints.

#### Creation of column to table
To create a column in the table, it is mandatory to specify the column name and datatype, while max-length, nullable, default-value attributes are optional. 

#### Addition of columns to existing tables
Inorder to add an additional column in the table we need to specify the column tag.

#### Dropping an existing column
You can delete columns in particular tables that you prefer to take out. While dropping a column, note that 
You can't delete a column that has primary or foreign key constraints.
When you delete a column from a table, the column and all the data it contains are deleted.

## Primary-key tag
 Primary keys must contain unique values and cannot have NULL values.
 A table can have only one primary key column.
 Each primary key should be provided within the <primary-keys> tag.

```
<primary-keys>
    <primary-key name = "Table_PK" column = "ID" sequence-generator = "TablePk.ID" />
</primary-keys>
```

### *Attributes of primary-key tag*

* **name =** The primary key name must be specified in a specific pattern,
like `format: TableName_[A-Za-z0-9_]`. Here the table name should be given first followed by `[A-Za-z0-9_]`. These values should not contain any special characters except underscore(_).

* **column =** Having an ID column as the primary key is always a good idea because it will never change.

* **sequence-batch =** It denotes the sequence increment value. And here, by default, the value is 50. If we want to set the value, it should not be less than 50.

* **sequence-generator =** Use sequences to automatically generate primary key values. It should be specified in the
    following `format: TableName_[A-Za-z0-9_]`.

#### Create a primary key in a new table
A table should comprise of only one primary key column which is a mandatory column in all tables.

#### Modifying a primary key in an existing table
If you want to redefine the primary key, the existing primary key relation should be modified. 

## Foreign-key tag
A foreign key is a field or collection of fields in one table that refers to the primary key in another table. Each foreign key should be defined within the foreign-keys tag.

``` 
<foreign-keys> 
    <foreign-key name="Table_FK" reference-table="Table1" local-column="Table1_ID" reference-column="ID 
        constraint="ON-DELETE-CASCADE" /> 
</foreign-keys>
```
	
### *Attributes of foreign-key tag*
		
* **name =** The primary key name must be specified in a specific pattern,
like `format: TableName_[A-Za-z0-9_]`. Here the table name should be given first followed by `[A-Za-z0-9_]`. These values should not contain any special characters except underscore(_).

* **reference-table** = It contains the name of the table from which the foreign key is chosen.

* **local-column =** The column refers the name to be alloted in the local table for the foreign key chosen.

* **reference-column =** The column denotes the name specifies for the foreign key in the reference table.
    
* **constraint =** Two types of foreign key constraints are allowed. And these constraints should be in upperCase.
While deleting a record, which is having foreign key relation in other table, following constraints are used for deleting the essential data.
     * `ON_DELETE_RESTRICT:`It doesn't delete the record mapped with the relation.
     * `ON_DELETE_CASCADE:` It deletes the record mapped with the relation.
     * `ON_DELETE_SET_NULL:` It replaces all the mapped record with null value.

#### Create a foreign key
To create a foreign key column in local table, it is necessary to use foreign key tag with any one of the following constraints ON DELETE CASCADE, ON_DELETE_SET_NULL and ON DELETE RESTRICT. We must provide 
these constraints else it raise an error that it was unable to parse the table.

#### Modify a foreign key
To modify a foreign key constraint by altering the reference column or reference table, you must first modify the existing foreign key constraint and then re-create it with the new definition.

#### Delete a foreign key
Deleting the foreign key relation deletes the mapping between the two tables.

## Unique-key tag
 The unique key ensures that all values in a column are different. When one or more than one field/column of a table uniquely identifies a record. The <unique-keys> tag consists of one or more <unique-key>.

``` 
<unique-keys> 
   <unique-key name="UniqueKey_UK">
      <unique-key-column>ID</unique-key-column>
   </unique-key> 
</unique-keys>
```

### *Attributes of unique-key tag*

* **name =** The primary key name must be specified in a specific pattern,
like `format: TableName_[A-Za-z0-9_]`. Here the table name should be given first followed by `[A-Za-z0-9_]`. These values should not contain any special characters except underscore(_).

#### *Inner Tag*

* **unique-key-column =** Valid column name should be provided. The unique key column values should be distinct.
	
#### Create a unique key
We can create one or more than one field/columns of a table that uniquely identify a record. Creating a unique constraint automatically creates a
corresponding unique index. It is mandatory to provide the name attribute and <unique-key-column> tag.  
If tag is not specified it raises unable to parse the table error.

#### Modify a unique key
If you prefer to alter the unique key column make sure that the field to not set null and allows unique values.

#### Delete a unique key
Deleting a unique constraint removes the requirement for a uniqueness for values entered in the column or combination of columns included in the constraint expression and deletes the corresponding unique index.

## Indexes tag
Indexes can be used to speed up data retrieval. It comprises the name of the table in <index> tag and column name to be retrieved fast in <index-column> tag. 
```
 <indexes> 
    <index name="Index_Id">
        <index-column>NAME</index-column>
    </index> 
</indexes>
```
### *Attributes of indexes tag*
* **name =** The primary key name must be specified in a specific pattern,
like `format: TableName_[A-Za-z0-9_]`. Here the table name should be given first followed by `[A-Za-z0-9_]`. These values should not contain any special characters except underscore(_).

#### *Inner Tag*

* **index-column =** The required column name in the index table should be provided.

#### Create an indexes
Create an index name for one or more column. Must provide the name attribute for indexes
which is in the pattern as tablename_Idx1 and <index-column> tag. We must provide these <index-column> tag, else it raise an error (unable to parse the table).

#### Modify an indexes
To provide additional columns in <index-column> tag for retrieval or to get data from other tables we need to modify the <index> tag. 

#### Delete an indexes
If one or more fields are in <index-column> is to be removed then we just need to remove the corresponding column tag. If you prefer not to have an index column, then you may remove the whole index tag. 

### *meta.xml precedent*
* A sample meta.xml is shown for reference 
	
```
<?xml version="1.0" encoding="utf-8" ?>
<metadata>
    <module name="module_crm_contact">
	    <table name="Sample" type="11">
            <columns>
                <column name="ID" data-type="BIGINT"/>
                <column name="NAME" data-type="CHAR" nullable="false" />
		            <column name="COMPANY_ID" data-type="BIGINT" default-value="1">
                <column name="PROVIDER_ID" data-type="CHAR" nullable="false" />
            </columns>
            <primary-key name="SAMPLE_PK" column="ID" sequence-generator="SAMPLE.ID" />
	          <foreign-keys>
                <foreign-key name="SAMPLE_ID_FK" reference-table="COMPANY"
                             local-column="COMPANY_ID" reference-column="ID" constraint="ON-DELETE-CASCADE"/>
            </foreign-keys>
	          <unique-keys>
                <unique-key name="SAMPLE_UK">
                    <unique-key-column>PROVIDER_ID</unique-key-column>
                </unique-key>
            </unique-keys>
            <indexes>
                <index name="SAMPLE_Idx">
                    <index-column>NAME</index-column>
                </index>
            </indexes>
        </table>
	</module>
</metadata>
```

## Data.xml
The data.xml file used to load, process, update and populate the data to fetch and configure the column of the table. In this, we can statically enter a record for the table. Using this data file we could be able to manipulate the data in the user's preferable way.

The name for the module should be declared inside the module tag <module>. We can create one or more modules.
 >``<module name="tlc_crm_contact">``

For the table in which you prefer to store static data, then inside the table tag for each table, the id must be mentioned and then followed by the column value. A valid ID consists of a table name, ID, and CO(column) and gives the Id value is acceptable formats separated by a colon. The format for the ID has to be `ID="TableName:ID: CO:1"`

> ``<TableName ID="" col1="" col2=""/>``

Example to enter a record statically You can add rows to a table: 
> ``<TableName ID="TableName:ID:CO:1" NAME="SampleTable"/>``

The sub-tag refers to a foreign key in a child table that will generally reference a primary key in the parent table under a parent tag
 ```
<parent-table ID=""  col="">
   <child-table ID="" col = "" ></child-table>
<parent-table> 
```
#### Insert a row into a table
First, the table in which you want to insert a new row, mention it, and following, set out the column that you prefer where the id field should be mandatory.
```
 <module name="tlc_crm_module">
    <Sample ID ="Sample:ID:CO:1" NAME="" COMPANY_ID ="COMPANY:ID:CO:1" PROVIDER_ID ="PROVIDER:ID:CO:1"/>
 </module>
```
#### Insert multiple rows into a table
If multiple rows are inserted in the same table with a different id.

```
<module name="tlc_crm_module">
    <Sample ID ="Sample:ID:CO:1" NAME="" COMPANY_ID ="COMPANY:ID:CO:1" PROVIDER_ID ="PROVIDER:ID:CO:1"/>
    <Sample ID ="Sample:ID:CO:2" NAME="" COMPANY_ID ="COMPANY:ID:CO:2" PROVIDER_ID ="PROVIDER:ID:CO:2"/>
    <Sample ID ="Sample:ID:CO:3" NAME="" COMPANY_ID ="COMPANY:ID:CO:3" PROVIDER_ID ="PROVIDER:ID:CO:3"/>
 </module>
 ```
**Note:** Id must be unique it cannot contain duplicates.

#### Foreign key relation in same module
If we insert static data for foreign key relationships in the same module. Make sure that the child table is the subtag for the parent table. In child tag it is not necessary to provide the id of parent as it already refers it from parent tag.
The data has been mapped in  both child and parent table.

#### Foreign key relation in different module
Mapping the static data using a foreign key in a different module. It denotes the parent table from one module and the child table from another. For referencing the parent table in child tag it is mandatory to provide id since it is referred from other module.
And it mapped to the corresponding tables.

### *data.xml file precedent*

```
<?xml version="1.0" encoding="utf-8" ?>
<dumpdata>
 <module name = "tlc_crm_contact"
   <MCMFieldSQLTable ID="MCMFieldSQLTable:ID:CO:6" TABLE_NAME="MCOLifeCycleStage"/>
     <MCMDataProvider ID="MCMDataProvider:ID:CO:50" TYPE="1" NAME="COMPANY_LIST">
        <MCMFields ID="MCMFields:ID:CO:50" NAME="id" DISPLAY_NAME="i18n.contact.company.id.display.name" 
	            PRIMARY="true" SORT="1" SEARCHABLE="false" ORDER="5" HIDDEN="true">
            <MCMFieldSQLSource ID="MCMFieldSQLSource:ID:CO:50" TABLE_ID="MCMFieldSQLTable:ID:CO:1" COLUMN="ID" TYPE="" FLAG=""/>
        </MCMFields>
      </MCMDataProvider>
 </module>    
</dumpdata>
```
## Packages
  `api` -  A collection of interfaces with their respective methods, fields, and constructors. It provides the services for data containers, queries, constraints, and clauses.

  `internal` - It handles all the implementation of DDL, DQL, DML related queries.

  `resource` - Auto-generated table content class file. 

  `update.ddl` - Provides interface for DDL-related actions.

### Api Package

* **DML -** The DML commands in Structured Query Language change the data present in the SQL database. We can easily access, store, modify, update and delete the existing records from the database using DML commands, Here it provides the services for all related DML actions.

* **ds -** A data structure is a special way of organizing and storing data in a database so that it can be used efficiently. Provides services for admindatastore, orgdatastore, readable and writable datastore.

* **listener -** Provides services for [Row listeners](#row-listener) like row added, row updated, and row deleted listeners.

* **meta -**  Meta-SQL is a great way to abstract SQL logic and ensures consistency in SQL definitions. Also, it tells about SQL statements text with key fields such as table type and datatype.

* **sequence -** A sequence is a set of integers that allows the automatic generation of values and is supported by some database systems to produce unique values on demand. provides the service for sequence generator.

### internal package
* **data -** Data is information that can be organized and stored in a database. For that, it provides the [DataContainer](#datacontainer), and here it can be also filtered the data container according to our needs.

* **dml -** Provides the services for DML queries in the API package. And layout the implementation for all services here.

* **ds -** Provides implementations for readable and writable datastore, admindatastore, and orgdatastore.

* **handler -** When an SQL procedure executes if unfortunately, an error occurs then the procedure ends unless. And tell the procedure to perform some other action. These Handler Statements are abstract methods for all DML and DDL-related queries.

* **listener -** Listen to the Row it records the cache for add, update and delete process.

* **meta -** Provides and maintains the cache process in the data container and also executes the procedure for handling metadata.

* **parser -** Provides implementations for metadata parser, constraints resolver, table definition loader, etc.

* **pgsql -** PostgreSQL is an object-relational database used as the primary data store. provides services for DML and DDL-related functionalities.

* **sequence -** Generate the sequence automatically, it loads the series.

* **status -** Error code is a numeric or alphanumeric code that is used to determine the nature of an error and why it occurred. when they attempt to do something or fail to do and they can be passed off to error handlers that determine what action to take. And here, they provide the status for the error code that occurred by this module. 

* **update -** Modify or revert the existing records in a table it provides the pre-action type(create_table, drop_unique_key, create_index, update_index, delete_index,...) and post-action type(create_unique_key, update_unique_key, create_foreign_key, update_foreign_key, drop_foreign_key,...).

# DataContainer
DataContainer provides the enum for insert, delete and update.  Data retrival from DML and DQL queries can be stored in Data container as rows. From this container we can retrieve the required rows of the required table by streaming the rows, and by using whereclause.

# Row Listener
An interface that must be implemented by a component that wants to be notified when a significant event happens in the life of a Row set object. It called when a row is inserted, updated, or deleted. The listener will be notified whenever an event occurs on this Row set object. Creates, updates or Removes the designated object from this Row set object's list of listeners.

## Types Of Row Listener
There are 11 types of listeners and they are in the form of interface.

#### *IgnoreIfExists :* 
Provides a `ignore` method returns in boolean format while inserting a data into table if wanted to check condition that it has been already exists then insert should not happen else record should be inserted.

#### *ListenerOrder :*
The listener will check for row entry. Everything will happen automatically, you just need to enable the listener once. Also it based on the priority which the listener order extends as in enum.
Manually, we can Set the values for each priority. By default, it set the values of MEDIUM_PRIORITY
```
 HIGH_PRIORITY - 0
 REGISTRY_PRIORITY- 25
 MEDIUM_PRIORITY - 50
 LOW_PRIORITY - 100 
```
#### *OutOfRangeNotification :* 
Notified when the process exists out of range and provides a method `processOutOfRangeNotification` returns in boolean.

#### *IgnoreUpdate :*
 Checks the row is already exists or not by extending the `IgnoreIfExists` and provides a method ignoreUpdate passing a parameter as table and columns, it returns a boolean whether it already exists or not.

#### *IgnoreDelete :*
 Checks the row is already exists or not by extending the `IgnoreIfExists` and provides a method ignoreParentDeleteAction passing a parameter as table and parent primary key and it returns a boolean whether it already exists or not.

#### *RowAddListener :* 
RowAddListener provides a method addRows, passing a collection of rows as a parameter and which it extends `IgnoreIfExists` and `ListenerOrder`.

#### *RowUpdateListener :* 
RowUpdateListener Provides a method updateRows passing a collection of rows and also it extends the functionalities of `IgnoreUpdate`,` OutOfRangeNotification`, and `ListenerOrder`.

#### *RowDeleteListener :*
 RowDeleteListener Provides a method deleteRows passing a collection of rows  and also it extends the functionalities of `IgnoreDelete`, `OutOfRangeNotification`, and `ListenerOrder`.

#### *RowIdDeleteListener :*
 RowIdDeleteListener provides a method deleteRows passing a parameter as set of id's. Row is deleted based on its Id. Which it extends the functionalities of `IgnoreDelete`, `OutOfRangeNotification`, and `ListenerOrder`.

 #### *RowListener :* 
The given interface extends the functionalities of `RowAddListener`,`RowUpdateListener`, and `RowIdDeleteListener`. Once we access the rowListener we can also be able to access the above functionalities.

#### *CompleteRowListener :* 
The given interface extends the functionalities of `RowAddListener`, `RowUpdateListener`, and `RowDeleteListener`. Once we access the CompleteRowListener we can also be able to access the above functionalities. 

## RowListenerHandler
The listener will be handled whenever an event occurs on this Row set object. It provides the specific methods for all the types of listeners. Passing the parameter as Table and the listener type. All the methods are accesd to the rowListenerContainer.

## RowListenerContainer
The rowListenerContainer listen every time when we add, update or delete a list of rows and it will be stored as a cache every time based on ListenerOrder priority and the list of add, update or delete listener.
The collection of rows get added, updated, or deleted in their respective tables. The above functions listen and get stored as a form of cache.
  





