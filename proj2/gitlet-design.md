# Gitlet Design Document

**Name**:

## Classes and Data Structures

### Class 1

#### Commit

##### Variables
1. message - The commit message that the user pass in.
2. timestamp - The time that the user make a commit.
3. parent - point to the parent of the current commit.
4. CommittedFile - The collections of the files that the current commit possess.

##### Methods
1. Commit() - create the initialed commit
2. Commit(...) - create the following commits
3. saveCommit() - to create the commit text to store the commit object as a file.
4. gets - get the private variable
5. makeChange() - make the commit unique from its parent
6. getParent() - get the current commit 's parent
 
* 

### Class 2

#### StagingArea

##### Variable
1. addition -to stage the added things
2. removal - for the rm implements

##### methods
1. 

### Class 3



## Algorithms

## Persistence

