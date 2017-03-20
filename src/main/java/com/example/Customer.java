package com.example;

public class Customer {
	
	private int id;
	private String CodiceCliente, Indrizzo, CodiceAzienda, provincia, lat, longitude;
	
	public Customer(int id, String CodiceCliente, String Indrizzo, String provincia, String lat, String longitude) {
        this.id = id;
        this.CodiceCliente = CodiceCliente;
        this.Indrizzo = Indrizzo;
        this.longitude = provincia;
        this.lat = lat;
        this.longitude = longitude;
    }
	
	public Customer(String CodiceCliente, String CodiceAzienda, String Indrizzo) {
		this.id = id;
		this.CodiceAzienda = CodiceAzienda;
		this.Indrizzo = Indrizzo;
		this.CodiceCliente = CodiceCliente;
		}

	public Customer() {
		// TODO Auto-generated constructor stub
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setCodiceCliente(String CodiceCliente) {
		this.CodiceCliente = CodiceCliente;
	}
	
	public void setCodiceAzienda(String CodiceAzienda) {
		this.CodiceAzienda = CodiceAzienda;
	}
	
	public void setIndrizzo(String Indrizzo) {
		this.Indrizzo = Indrizzo;
	}
	/*public int getId() {
		return this.id;
	}*/
	public String getCodiceCliente() {
		return this.CodiceCliente;
	}
	/*public String getProvincia() {
		return this.provincia;
	}*/
	public String getLatitude() {
		return this.lat;
	}
	public String getLongitude() {
		return this.longitude;
	}
	/*public String getCodiceAzienda() {
		return this.CodiceAzienda;
	}
	*/
	public String getIndrizzo() {
		return this.Indrizzo;
	}


	
}