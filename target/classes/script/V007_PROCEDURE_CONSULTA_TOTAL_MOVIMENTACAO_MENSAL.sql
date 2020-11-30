CREATE OR REPLACE PROCEDURE MVFIN.PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_MENSAL (MES IN NUMBER, ANO IN NUMBER, VALOR OUT VARCHAR2)
IS
P_VALOR NUMERIC;
BEGIN
   SELECT SUM(TM.VALOR)
     INTO P_VALOR
     FROM TB_MOVIMENTACAO TM
    WHERE TO_CHAR(TM.DATA_MOVIMENTO, 'MM') = MES
      AND TO_CHAR(TM.DATA_MOVIMENTO, 'YYYY') = ANO;
   VALOR := P_VALOR;
END PROCEDURE_CONSULTA_TOTAL_MOVIMENTACAO_MENSAL;