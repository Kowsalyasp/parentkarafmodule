# SQL MODULE



## TABLE OF CONTENTS

- [Abstract](#abstract)
- [Requirements](#requirements)
- [BundleListener](#bundlelistener)
- [SQLDevListener](#sqldevlistener)
- [Connection](#connection)
- [MetaData](#metadata)
  * [*meta.xml file tags precedent*](#metaxml-file-tags-precedent)
    * [*Attributes of table tag*](#attributes-of-table-tag)
  * [*Column*](#column-tag)
    * [*Attributes of column tag*](#attributes-of-column-tag)
  * [Primary-key*](#primary-key-tag)
    * [*Attributes of primary key tag*](#attributes-of-primary-key-tag)
  * [*foreign-key*](#foreign-key-tag)
    * [*Attributes of foreign-key tag*](#attributes-of-foreign-key-tag)
  * [*unique-key*](#unique-key-tag)
    * [*Attributes of unique-key tag*](#attributes-of-unique-key-tag)
  * [*Indexes*](#indexes-tag)
    * [*Attributes of indexes tag*](#attributes-of-indexes-tag)
  * [*meta.xml precedent*](#metaxml-precedent)
- [Data.xml](#dataxml)
  * [*data.xml file precedent*](#dataxml-file-precedent)
- [Packages](#packages)
  * [api package](#api-package)
  * [internal package](#internal-package)



## Abstract:
* A module can be thought of as an object library that is linked to the application code. 
* The procedures could be compiled into object code and linked directly to the application code, they could be compiled 
  and stored on the DBMS and calls to access plan identifiers placed in the application code, or they could be interpreted at run time.
* The SQL module allows you to execute custom queries against an SQL database and store the results in Elasticsearch.
  This module supports the databases.
* The SQL module explains how to use simple DataBase Query implementations.
* This module explains the statically available methods for executing DML, DDL and DQL-related database actions.

## Requirements:
This odule requires the following maven dependencies.
	
1. javatuples - A tuple is a collection of several elements that may or may not be related to each other. 
	            In other words, tuples can be considered anonymous objects.
2. HikariCP - HikariCP is a solid high-performance JDBC connection pool. 
		      A connection pool is a cache of database connections maintained so that the connections can be reused 
		      when future requests to the database are required.
3. PostgreSQL - PostgreSQL is an advanced, enterprise-class open-source relational database that 
	            supports both SQL (relational) and JSON (non-relational) querying.	
	
## BundleListener:
	A BundleEvent listener When a BundleEvent is fired, it is asynchronously delivered to a BundleListener. BundleListeners are called with a BundleEvent object when a bundle has been installed, resolved, started, stopped, updated, unresolved, or uninstalled.
	
## SQLDevListener:
	Utilizing the bundle lifecycle, it will perform actions like metadata parsing, etc. The class explains the loading, processing, updating, and populating of the meta and data XML files. Make use of this. It notes the bundle history record. We can see this in snapshots of our system. Hang on to the bundle history, which compares both the existing XML files and the current XML file to upload the data into the database. 
      
### Connection:
    In the karaf folder, create the `configuration folder` which stores and loads all the configuration files like database connectivity, cache server, and web server.

For databse connection:
    create the `configuration source file` with a name `database.pgsql` with extension `cfg` 
(eg: database.pgsql.cfg)
          
A configuration file is a properties file containing key/value pairs:
 The cfg file provides a set of commands to manage the configuration.

> property = value

You can add your own initial configuration directly in the file. To get the database service, mention the following mandatory properties in the given format.

The database name should be TLC(capitalized), and to create the database with the same name in database.
> database.name = TLC

To get the database server connection mention the port number as follow : `localhost:portnumber`. 
> database.server = localhost:5432

A default username and password that is connected to the database( hosting-db ). 
> database.username = userName
database.password = password

This property helps to get the environment and the mode to be `enabled` if it is true; otherwise, if it is false, it won't connect with the database.
> development.mode.enabled = true

Additionally we can use following properties to mention the pool size

The property controls the maximum size that the pool is allowed to reach. 
Basically this value will determine the maximum number of actual connections to the database backend.
> connection.pool.max = 

The property controls the minimum number of idle connections to maintain in the pool.
> connection.pool.idle = 

The property controls the maximum amount of time (in milliseconds) that a connection is allowed to sit idle in the pool. A connection will never be retired as idle before this timeout. A value of 0 means that idle connections are never removed from the pool.
> connection.pool.idle.timeout = 
	
## MetaData:
	The meta.xml file to load and populate the metadata to fetch and configure the table definitions and initialise the sequence generation of primary keys. The main purpose of meta.xml is to create the table. We set up the metadata with all the essential element tags like table name, columns, and constraints to configure the table in the database. And the module tag refers to which module that wants to create the table.
	
The meta.xml file presents a set of metadata, such as the module name, the tables to include.
XML is a textual data format that is widely used for the representation of data.
Used an XML-based language to describe the metadata for resources by using the tags below:

## Table tag
Create a table with the tablename and type which are present inside the table tag. Each table has a table type value which can be given as per your needs.

``` 
<table name="TableName" type="11"> </table>
 ```

 ### *Attributes of table tag*

    name = Name of the table.
    type = It refers to table type, and some values are given for each table types. the following table types are,
               1 -> COMMON 
                  If you don't specify any type, by default the table type is set to common. It does not depend on any ID.
               2 -> COMMON_PARTITIONED_BY_ID
                  If the table type is common but if the table wants to be partitioned by ID, then prefer this table type.
               11 -> ORG_DEPENDENT
                  The table which is dependent on org_id.
               12 -> ORG_PARTITIONED_BY_ID
                  Based on ID, the table would be partitioned.
               13 -> ORG_PARTITIONED_BY_ORG
                  Based on org_id, the table would be partioned.
               21 -> ORG_MIXED
                  This type of table belongs to org_id and also exists common null values.
               22 -> ORG_MIXED_BY_ID
                  This type of table belongs to ID, org_id and also exists common null values.
               23 -> ORG_MIXED_BY_ORG
                  This type of table belongs to org_id, partition by ID and also exists common null values.
    
## Column tag
Columns are also called fields in a database table. The attributes for the column name of the table,
its data type, whether it is nullable or not, maximum length, and default value for the column are provided here. 
Each column should be provided within the columns tag.

```
<columns>
    <column name="NAME" data-type="CHAR" max-length="30" nullable="false" default-value="1"/>
</columns>
```
 ### *Attributes of column tag*

    name = It refers to the column name.
    data-type = It refers to what type of column it is, and here we can use some specific data types.
         supported datatypes:
              BIGINT: An instance of the long datatype
              INTEGER: An instance of the integer datatype.
              SMALLINT: An instance of the short datatype.
              KCHAR: An instance of the i18n datatype.
              SCHAR: An instance of the small character datatype.
              BLOB: An instance of the byte datatype.
              CHAR: An instance of the character datatype. 
              TEXT: The variable-length character SQL text data type is called VARCHAR
              STEXT:
              BOLLEAN: A boolean is an expression that evaluates to either true or false.
  
 nullable = Whether we want to allow null values for the specific column, we set nullable = true; otherwise, nullable = false.
 max-length = It refers to the maximum length that a column should be provided.
 default-value = The default value is assigned to the column. It may be a boolean type.


## Primary-key tag
 Primary keys must contain unique values and cannot have NULL values.
 A table can have only one primary key, which may consist of single or multiple fields.
 Each primary-key should be provided within the <primary-keys>tag.

```
<primary-keys>
    <primary-key name="Table_PK" column="ID" sequence-generator="TablePk.ID" />
</primary-keys>
```

### *Attributes of primary-key tag*

   name = The primary key name must be specified in a specific pattern, such as first being table name and then with continuation [A-Za-z0-9_], these values are only allowed after table name and are separated by underscore.
         ` format: TableName_[A-Za-z0-9_]`
   column = Having an ID column as the primary key is always a good idea because it will never change.
   sequence-batch = It denotes the starts with and here, by default, the value is 50. If we want to set the value, it should not be      less than 50.
   sequence-generator = Use sequences to automatically generate primary key values. It should be specified in the following `format: TableName_[A-Za-z0-9_]`.

## foreign-key tag
A foreign-key is a field or collection of fields in one table that refers to the primary-key in another table.
Each foreign key can be accessed within the foreign-keys tag.

``` 
<foreign-keys> 
    <foreign-key name="Table_FK" reference-table="Table1" local-column="Table1_ID" reference-column="ID constraint="ON-DELETE-CASCADE" /> 
</foreign-keys>
```
	
### *Attributes of foreign-key tag*
		
   name = The foreign key name must be specified in a specific pattern, such as first being table name and then with continuation [A-Za-z0-9_], these values are only allowed after table name and are separated by underscore.
         `format: TableName_[A-Za-z0-9_]`
   reference-table = A table that is referenced from a referencing table with a foreign key.
   local-column = It refers to the local table.
   reference-column = Returns the item stored in the specified column within the context row based on a related column between them. 
   constraint = Three types of foreign key constraints are allowed. And these constraints are in caps.
         ON_DELETE_RESTRICT: If you want to delete a record from one table but there is a corresponding record in the other table, the delete operation is not allowed.
         ON_DELETE_CASCADE:To specify whether you want rows deleted in a child table when corresponding rows are deleted in the parent table.
         ON_DELETE_SET_NULL:
	
## unique-key tag
Multiple unique keys can be present in a table. NULL values are allowed in the case of a unique key. These can also be used as foreign keys for other tables.
Each unique key can be accessed within the unique-keys tag.

``` 
<unique-keys> 
   <unique-key name="UniqueKey_UK">
      <unique-key-column>ID</unique-key-column>
   </unique-key> 
</unique-keys>
```

### *Attributes of unique-key tag*

    name = The unique key name must be specified in a specific pattern, such as first being table name and then with continuation [A-Za-z0-9_], these values are only allowed after table name and are separated by underscore.
      ` format: TableName_[A-Za-z0-9_]`
    <unique-key-column> = Valid column name should be provided.
	
## indexes tag
Indexes can be used to speed up data retrieval. Simply put, an index is a pointer to data in a table.
Each index-key can be accessed within the indexes tag.

```
 <indexes> 
 <index name="Index_Id">
 <index-column>TABLE_NAME</index-column>
    </index> 
</indexes>
```
### *Attributes of indexes tag*
    name = The indexes name must be specified in a specific pattern, such as first being table name and then with continuation [A-Za-z0-9_], these values are only allowed after table name and are separated by underscore.
     ` format: TableName_[A-Za-z0-9_]`
    <index-column> = Valid column name should be provided.

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

## Data.xml:
	The data.xml file to load, process, update and populate the data to fetch and configure the column of the table.
	In this, we can statically enter an entity for the table.
	Using this data file we could be able to manipulate the data in the user's preferable way.

The name for the module should be declare inside the module tag <module>. We can create one or more module.
 
The format for module name should be tlc_crm_modulename. If it is contact module:
 >``<module name="tlc_crm_contact">``

For the table in which you prefer to store static data, then inside the tabletag for each table, the id must be mentioned and then followed by the column value,
A valid ID consists of a table name, ID, CO(column) and give the Id value in acceptable formats separated by a colon. The format for the ID have to be ID="MCMFieldSQLTable:ID:CO:1"

> ``<TableName ID="" col1="" col2=""/>``

Example to enter a record statically You can add rows to a table: 
> ``<MCMFieldSQLTable ID="MCMFieldSQLTable:ID:CO:1" TABLE_NAME="SampleTable"/>``

The sub-tag is a tag that is referred as a foreign key in a child table will generally reference a primary key in the parent table under a parent tag.
 ```
 <parent-table ID=""  col="">
   <child-table ID="" col = "" ></child-table>
<parent-table> 
```

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
       
</dumpdata>
```

	
## Packages:
	- api -  A collection of interfaces with their respective methods, fields, and constructors. It provides the services for data containers, queries, constraints, and clauses.
	- internal - It provides the services of api package.
	- resource - Auto-generated table content class file. 
	- update dll - The interface for DDL-related actions.

### api package:
* dml - The DML commands in Structured Query Language change the data present in the SQL database. We can easily access, store, modify,   update and delete the existing records from the database using DML commands, Here it provides the services for all related dml queries.
* ds - A data structure is a special way of organizing and storing data in a database so that it can be used efficiently. Provides  services for admindatastore, orgdatastore, readable and writable datastore.
* listener - Provides services for listeners like row added, row updated and row deleted listeners.
* meta - Meta-SQL is a great way to abstract SQL logic and ensure consistency in SQL definitions. Also it tells about SQL statements text with key fields such as tabletype and datatype.
* sequence - Sequence is a set of integers that it allows the automatic generation of values and supported by some database systems to produce unique values on demand. provides the service for sequence generator.
### internal package:
* data - Data is a information that can be organized and stored in a database. For that it provides the datacontainer and here it can be also filtered the datacontainer according to our needs.
* dml - Provides the services for dml queries in api package. And lay out the implementation for all servies here.  
* ds - Provides implementations for readable and writable datastore, admindatastore, and orgdatastore.
* handler - When an SQL procedure executes, if unfortunately error occurs then the procedure ends unless. And tell the procedure to perform some other action. These Handler Statements are abstract methods for all DML and DDL-related queries.  
* listener - Listen the Row it records the cache for add, update and delete process.
* meta - Provides and maintain the cache process in datacontainer and also executes the procedure for handling meta data.
* parser - Provides implementations for metadata parser, constraints resolver, table definition loader, etc.
* pgsql - PostgreSQL is an object-relational database used as the primary data store.provides services for dml and ddl related functionalities.
* sequence - Generate the sequence automatically, it loads the series.
* status - error code is a numeric or alphanumeric code that is used to determine the nature of an error and why it occurred.when they attempt to do something or fail to do and they can be passed off to error handlers that determine what action to take. And here, they provides the status for the error code occured by this module. 
* update - Modify or revert the existing records in a table it provides the pre action type(create_table, drop_unique_key, create_index, update_index, delete_index,...) and post action type(create_unique_key, update_unique_key, create_foreign_key, update_foreign_key, drop_foreign_key,...).


	
