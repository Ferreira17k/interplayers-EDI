package br.com.ciamed.connectciamed.ConnectCiamed.model.tables.interplayers;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Table(name = "tabela")
@Entity
@Getter
@Setter
public class InterplayersHistoricoModel {
    @Id
    private Long codHist;
    @Column(name = "USER_INSERT")
    private String userInsert;
    @Column(name = "COD_UNIDADE")
    private Long codUnidade;
    @Column(name = "COD_OC")
    private Long codOc;
    @Column(name = "FILE_NAME")
    private String fileName;
    @Column(name = "FILE_DATE")
    private Timestamp fileDate;
    @Column(name = "FILE_DATA")
    public byte[] fileData;
}


