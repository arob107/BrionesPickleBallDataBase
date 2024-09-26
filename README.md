"# BrionesPickleBallDataBase" 
This database includes Intensives (trainings), locations of the intensives, and customers associated with the trainings.
Intensives has a many-to-one relationship with locations (one location, many intensives), and a many-to-many relationship with customers. 
All relationships are bidirectional.
Locations and customers must reference an intensive when performing CRUD operations, with the exception of reading. 
