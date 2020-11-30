package com.mvfinapp.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mvfinapp.dto.TotalReceitaEmpresaDto;
import com.mvfinapp.model.MovimentacaoModel;
import com.mvfinapp.vo.TotalPorMovimentacaoVo;
import com.mvfinapp.vo.TotalSaldoInicialVo;

@Repository
public interface MovimentacaoRepository extends JpaRepository<MovimentacaoModel, Long> {

	@Query("SELECT m FROM MovimentacaoModel m WHERE m.id = :id")
	List<MovimentacaoModel> consultaMovimentacaoPorIdConta(@Param("id") Long id);
	
	
	@Query("SELECT new com.mvfinapp.vo.TotalPorMovimentacaoVo("
			+ " m.tipoMovimentacao,"
			+ " CASE"
			+ "     WHEN m.tipoMovimentacao = 'CREDITO' THEN sum(m.valor)"			
			+ "     WHEN m.tipoMovimentacao = 'DEBITO' THEN sum(m.valor)"
			+ " END as TOTAL"			
			+ " )"
			+ " FROM MovimentacaoModel m"
			+ " WHERE m.cliente.id = :id"
			+ " GROUP BY m.tipoMovimentacao")
	List<TotalPorMovimentacaoVo> totalMovimentacaoPorIdCliente(@Param("id") Long id );
	
	
	@Query("SELECT "			
			+ " CASE"
			+ "     WHEN COUNT(m) <= 10 THEN (COUNT(m) * 1.0)"
			+ "     WHEN COUNT(m) >= 10 AND COUNT(m) <= 20 THEN (COUNT(m) * 0.75)"
			+ "     WHEN COUNT(m) > 20 THEN (COUNT(m) * 0.50)"
			+ " END as TOTAL"			
			+ " FROM MovimentacaoModel m"
			+ " WHERE m.cliente.id = :id")
	BigDecimal valorPagoPelasMovimentacaoPorIdCliente(@Param("id") Long id );
	
	
	@Query(value = "SELECT new com.mvfinapp.vo.TotalSaldoInicialVo (m.valor)"
			+ " FROM MovimentacaoModel m "
			+ " WHERE m.cliente.id = :id"
			+ " AND tipoTransacao = 'RECEBIMENTOS'"			
			+ " ORDER BY dataMovimento ASC",
			countQuery = 
			"SELECT COUNT(m)"
					+ " FROM MovimentacaoModel m "
					+ " WHERE m.cliente.id = :id"
					+ " AND tipoTransacao = 'RECEBIMENTOS'"
					+ " ORDER BY dataMovimento ASC")
	Page<TotalSaldoInicialVo> saldoInicialPorIdCliente(@Param("id") Long id, Pageable page );
	
	
	@Query("SELECT SUM(m.valor) FROM MovimentacaoModel m WHERE m.tipoTransacao = 'RECEBIMENTOS' AND m.cliente.id = :id")
	BigDecimal totalRecebimentoPorIdCliente(@Param("id") Long id );
	
	@Query("SELECT SUM(m.valor) FROM MovimentacaoModel m WHERE m.tipoTransacao = 'PAGAMENTO' AND m.cliente.id = :id")
	BigDecimal totalPagamentoPorIdCliente(@Param("id") Long id );


	@Query("SELECT new com.mvfinapp.vo.TotalPorMovimentacaoVo("
			+ " m.tipoMovimentacao,"
			+ " CASE"
			+ "     WHEN m.tipoMovimentacao = 'CREDITO' THEN sum(m.valor)"			
			+ "     WHEN m.tipoMovimentacao = 'DEBITO' THEN sum(m.valor)"
			+ " END as TOTAL"			
			+ " )"
			+ " FROM MovimentacaoModel m"
			+ " WHERE m.cliente.id = :id"
			+ " AND m.dataMovimento >= :dataInicial AND m.dataMovimento <= :dataFim"
			+ " GROUP BY m.tipoMovimentacao")
	List<TotalPorMovimentacaoVo> totalMovimentacaoPorIdClientePeriodo(Long id, LocalDateTime dataInicial,
			LocalDateTime dataFim);

	@Query("SELECT "			
			+ " CASE"
			+ "     WHEN COUNT(m) <= 10 THEN (COUNT(m) * 1.0)"
			+ "     WHEN COUNT(m) >= 10 AND COUNT(m) <= 20 THEN (COUNT(m) * 0.75)"
			+ "     WHEN COUNT(m) > 20 THEN (COUNT(m) * 0.50)"
			+ " END as TOTAL"			
			+ " FROM MovimentacaoModel m"
			+ " WHERE m.cliente.id = :id"
			+ " AND m.dataMovimento >= :dataInicial AND m.dataMovimento <= :dataFim")
	BigDecimal valorPagoPelasMovimentacaoPorIdClientePeriodo(Long id, LocalDateTime dataInicial, LocalDateTime dataFim);

	@Query("SELECT SUM(m.valor) "
			+ " FROM MovimentacaoModel m "
			+ " WHERE m.tipoTransacao = 'RECEBIMENTOS' "
			+ " AND m.cliente.id = :id"
			+ " AND m.dataMovimento >= :dataInicial AND m.dataMovimento <= :dataFim"
			)
	BigDecimal totalRecebimentoPorIdClientePeriodo(Long id, LocalDateTime dataInicial, LocalDateTime dataFim);

	@Query("SELECT SUM(m.valor) FROM "
			+ " MovimentacaoModel m "
			+ " WHERE m.tipoTransacao = 'PAGAMENTO' "
			+ " AND m.cliente.id = :id"
			+ " AND m.dataMovimento >= :dataInicial AND m.dataMovimento <= :dataFim")
	BigDecimal totalPagamentoPorIdClientePeriodo(Long id, LocalDateTime dataInicial, LocalDateTime dataFim);

	@Query(value = "SELECT new com.mvfinapp.vo.TotalSaldoInicialVo (m.valor)"
			+ " FROM MovimentacaoModel m "
			+ " WHERE m.cliente.id = :id"
			+ " AND tipoTransacao = 'RECEBIMENTOS'"	
			+ " AND m.dataMovimento >= :dataInicial AND m.dataMovimento <= :dataFim"
			+ " ORDER BY dataMovimento ASC",
			countQuery = 
			"SELECT COUNT(m)"
					+ " FROM MovimentacaoModel m "
					+ " WHERE m.cliente.id = :id"
					+ " AND tipoTransacao = 'RECEBIMENTOS'"
					+ " AND m.dataMovimento >= :dataInicial AND m.dataMovimento <= :dataFim"
					+ " ORDER BY dataMovimento ASC")
	Page<TotalSaldoInicialVo> saldoInicialPorIdClientePeriodo(Long id, LocalDateTime dataInicial, LocalDateTime dataFim, Pageable page);


	@Query(" SELECT new com.mvfinapp.dto.TotalReceitaEmpresaDto("
			+ " tc.nome,"
			+ " COUNT(*) AS qtd,"
			+ " CASE"
			+ "   WHEN COUNT(*) <= 10 THEN (COUNT(*) * 1.00)"
			+ "   WHEN COUNT(*) >= 10 AND COUNT(*) <= 20 THEN (COUNT(*) * 0.75)"
			+ "   WHEN COUNT(*) > 20 THEN (COUNT(*) * 0.50)"
			+ " END AS total "
			+ " )"
			+ " FROM MovimentacaoModel tm"
			+ " JOIN ClienteModel tc ON tc.id = tm.cliente.id "
//			+ " AND tc.tipoCliente = 2"
			+ " GROUP BY tc.nome"
			+ "")
	List<TotalReceitaEmpresaDto> totalReceitaEmpresa(LocalDateTime dataInicial, LocalDateTime dataFim);

	@Query(value = "SELECT SUM(m.valor) FROM TB_MOVIMENTACAO m", nativeQuery = true)
	BigDecimal consultaTotalMovimento();
	
	@Query(value = "SELECT SUM(m.valor) FROM TB_MOVIMENTACAO m WHERE DATA_MOVIMENTO > SYSDATE - 7 ", nativeQuery = true)
	BigDecimal consultaTotalMovimentacaoSemana(); 

	@Query(value = "SELECT SUM(m.valor) FROM TB_MOVIMENTACAO m WHERE TO_CHAR(DATA_MOVIMENTO, 'MM') = :mes and TO_CHAR(DATA_MOVIMENTO, 'YYYY') = :ano", nativeQuery = true)
	BigDecimal consultaTotalMovimentacaoMensal(int mes, int ano);
		
	@Query(value = "SELECT SUM(m.valor) FROM TB_MOVIMENTACAO m where TO_CHAR(DATA_MOVIMENTO, 'YYYY') = :ano ", nativeQuery = true)
	BigDecimal consultaTotalMovimentacaoAno(int ano);


	
}
