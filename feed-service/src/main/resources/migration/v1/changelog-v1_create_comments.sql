--liquibase formatted sql

--changeset vasev-dm:1
create table if not exists public.comments(
    id serial primary key not null,
    author_id varchar(255) not null,
    item_id varchar(255) not null,
    text text not null
);
create index if not exists idx_comment_items
    on public.comments using btree(item_id);

--rollback drop table if exists public.comments;
--rollback drop index if exists idx_comment_items;
