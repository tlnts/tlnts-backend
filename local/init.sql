create role tlnts with password 'tlnts';
alter role tlnts with login;

create database feed;
grant all privileges on database feed to tlnts;

create database oauth;
grant all privileges on database oauth to tlnts;