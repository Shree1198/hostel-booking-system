create table booking (booking_time datetime(6), check_in datetime(6), check_out datetime(6), id bigint not null auto_increment, room_id bigint, user varchar(255), primary key (id))


create table room (is_available bit not null, id bigint not null auto_increment, room_number varchar(255), type varchar(255), primary key (id))