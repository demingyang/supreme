/*在使用序列之前，先把下面的表添加到数据库中*/


/*==============================================================*/
/* 序列生成器表: sys_seq_value                                         */
/*==============================================================*/
create table sys_seq_value
(
   seq_key              varchar(50) not null,
   seq_value            bigint not null,
   primary key (seq_key)
);

