CREATE OR REPLACE PROCEDURE MVFIN.PROCEDURE_CONSULTA_QUANTIDADE_MOVIMENTACAO (QUANTIDADE OUT VARCHAR2)
IS
P_QUANTIDADE NUMERIC;
BEGIN
   SELECT COUNT(*) 
     INTO P_QUANTIDADE
     FROM TB_MOVIMENTACAO tm;
   QUANTIDADE := P_QUANTIDADE;
END PROCEDURE_CONSULTA_QUANTIDADE_MOVIMENTACAO;