    CREATE DATABASE platform_jirasd;
CREATE USER admin PASSWORD 'admin_pwd';
GRANT ALL PRIVILEGES ON DATABASE platform_jirasd TO admin;

\c platform_jirasd;

CREATE USER jirasd_user PASSWORD 'jirasd_user_pwd';
GRANT CONNECT ON DATABASE platform_jirasd TO jirasd_user;

CREATE SCHEMA jirasd;
ALTER SCHEMA jirasd OWNER TO admin;
GRANT ALL PRIVILEGES ON SCHEMA jirasd TO jirasd_user;
ALTER DEFAULT PRIVILEGES
    FOR USER admin
    IN SCHEMA jirasd
    GRANT ALL PRIVILEGES ON TABLES TO jirasd_user;
ALTER DEFAULT PRIVILEGES
    FOR USER admin
    IN SCHEMA jirasd
    GRANT ALL PRIVILEGES ON SEQUENCES TO jirasd_user;