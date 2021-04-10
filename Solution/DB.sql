


mysql -u root -p
create database mylogs;
use mylogs;
CREATE TABLE IF NOT EXISTS visits_stats (
    v_id INT AUTO_INCREMENT PRIMARY KEY,
   timestamp VARCHAR(255) ,
    number_visit Long
) ;

