# In_File_Memory_System
Design and implement an in-memory file system. This file-system consists of 4 types of entities: Drives, Folders, Text files, Zip files

These entities, very much like their “real” file-system counterparts, obey the following relations.
a.	A folder, a drive or a zip file, may contain zero to many other folders, or files (text or zip).
b.	A text file does not contain any other entity.
c.	A drive is not contained in any other entity.
d.	Any non-drive entity must be contained in another entity.
If entity A contains entity B then we say that A is the parent of B.

Every entity has the following properties:
•	Name - An alphanumeric string. Two entities with the same parent cannot have the same name. Similarly, two drives cannot have the same name.
•	Path – The concatenation of the names of the containing entities, from the drive down to and including the entity. The names are separated by ‘\’.
•	Size – an integer defined as follows:
	For a text file – it is the length of its content.
	For a drive or a folder, it is the sum of all sizes of the entities it contains.
	For a zip file, it is one half of the sum of all sizes of the entities it contains.
Text files have an additional property
•	Content – A string of text. 

The system should be capable of supporting file-system like operations

1)	Create – Creates a new entity.
Arguments: Type, Name, Path of parent.
2)	Delete – Deletes an existing entity (and all the entities it contains).
Arguments: Path
3)	Move – Changing the parent of an entity.
Arguments: Source Path, Destination Path. 
4)	WriteToFile – Changes the content of a text file.
Arguments: Path, new Content
