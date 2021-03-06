CREATE OR REPLACE PROCEDURE MVFIN.PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_SEMANA (VALOR OUT VARCHAR2)
IS
P_VALOR NUMERIC;
BEGIN
   SELECT SUM(TM.VALOR)
     INTO P_VALOR
     FROM TB_MOVIMENTACAO TM
    WHERE DATA_MOVIMENTO > SYSDATE - 7;
   VALOR := P_VALOR;
END PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_SEMANA;