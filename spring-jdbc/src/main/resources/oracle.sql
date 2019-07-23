CREATE TABLE BOOKS(
    ID NUMBER GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
    NAME VARCHAR2(100) NOT NULL,
    PRICE NUMBER(15, 2) NOT NULL,
    CONSTRAINT book_pk PRIMARY KEY (ID)
);

/* Stored Procedure - Single result */
CREATE OR REPLACE PROCEDURE get_book_by_id(
    p_id IN BOOKS.ID%TYPE,
    o_name OUT BOOKS.NAME%TYPE,
    o_price OUT BOOKS.PRICE%TYPE)
AS
BEGIN

    SELECT NAME , PRICE INTO o_name, o_price from BOOKS WHERE ID = p_id;

END;

/* Stored Procedure - REF Cursor for result set */
CREATE OR REPLACE PROCEDURE get_book_by_name(
   p_name IN BOOKS.NAME%TYPE,
   o_c_book OUT SYS_REFCURSOR)
AS
BEGIN

  OPEN o_c_book FOR
  SELECT * FROM BOOKS WHERE NAME LIKE '%' || p_name || '%';

END;

/* Stored Function */
CREATE OR REPLACE FUNCTION get_price_by_id(p_id IN BOOKS.ID%TYPE)
RETURN NUMBER
IS o_price BOOKS.PRICE%TYPE;
BEGIN
    SELECT PRICE INTO o_price from BOOKS WHERE ID = p_id;
    RETURN(o_price);
END;

select get_price_by_id(2) from dual;

/* Stored Function */
CREATE OR REPLACE FUNCTION get_database_time
RETURN VARCHAR2
IS o_date VARCHAR2(20);
BEGIN
    SELECT TO_CHAR(SYSDATE, 'DD-MON-YYYY HH:MI:SS') INTO o_date FROM dual;
    RETURN(o_date);
END;

select get_database_time from dual;



