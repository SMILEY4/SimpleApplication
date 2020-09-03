package de.ruegnerlukas.simpleapplication.testapp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Countries {


	@Getter
	@AllArgsConstructor
	public static class Country {


		private final String name;

		private final int randomNumber;




		@Override
		public boolean equals(final Object o) {
			if (o instanceof Country) {
				Country other = (Country) o;
				return other.getRandomNumber() == getRandomNumber() && other.getName().equals(getName());
			} else {
				return false;
			}
		}




		@Override
		public int hashCode() {
			int result = getName() != null ? getName().hashCode() : 0;
			result = 31 * result + getRandomNumber();
			return result;
		}




	}




	public static List<Country> getAllAsObjStartingWith(final String str) {
		if (str == null || str.isEmpty()) {
			return getAllAsObj();
		} else {
			return getAllAsObj().stream()
					.filter(s -> s.getName().startsWith(str))
					.collect(Collectors.toList());
		}
	}




	public static List<Country> getAllAsObj() {
		final Random random = new Random(125);
		return getAll().stream()
				.map(name -> new Country(name, random.nextInt(10000)))
				.collect(Collectors.toList());
	}




	public static List<String> getAllStartingWith(final String str) {
		if (str == null || str.isEmpty()) {
			return getAll();
		} else {
			return getAll().stream()
					.filter(s -> s.startsWith(str))
					.collect(Collectors.toList());
		}
	}




	public static List<String> getAll() {
		List<String> countries = new ArrayList<>();
		countries.add("Afghanistan");
		countries.add("Albania");
		countries.add("Algeria");
		countries.add("Andorra");
		countries.add("Angola");
		countries.add("Antigua and Barbuda");
		countries.add("Argentina");
		countries.add("Armenia");
		countries.add("Australia");
		countries.add("Austria");
		countries.add("Azerbaijan");
		countries.add("Bahamas");
		countries.add("Bahrain");
		countries.add("Bangladesh");
		countries.add("Barbados");
		countries.add("Belarus");
		countries.add("Belgium");
		countries.add("Belize");
		countries.add("Benin");
		countries.add("Bhutan");
		countries.add("Bolivia");
		countries.add("Bosnia and Herzegovina");
		countries.add("Botswana");
		countries.add("Brazil");
		countries.add("Brunei");
		countries.add("Bulgaria");
		countries.add("Burkina Faso");
		countries.add("Burundi");
		countries.add("Cabo Verde");
		countries.add("Cambodia");
		countries.add("Cameroon");
		countries.add("Canada");
		countries.add("Central African Republic (CAR)");
		countries.add("Chad");
		countries.add("Chile");
		countries.add("China");
		countries.add("Colombia");
		countries.add("Comoros");
		countries.add("Democratic Republic of the Congo");
		countries.add("Republic of the Congo");
		countries.add("Costa Rica");
		countries.add("Cote d'Ivoire");
		countries.add("Croatia");
		countries.add("Cuba");
		countries.add("Cyprus");
		countries.add("Czech Republic");
		countries.add("Denmark");
		countries.add("Djibouti");
		countries.add("Dominica");
		countries.add("Dominican Republic");
		countries.add("Ecuador");
		countries.add("Egypt");
		countries.add("El Salvador");
		countries.add("Equatorial Guinea");
		countries.add("Eritrea");
		countries.add("Estonia");
		countries.add("Ethiopia");
		countries.add("Fiji");
		countries.add("Finland");
		countries.add("France");
		countries.add("Gabon");
		countries.add("Gambia");
		countries.add("Georgia");
		countries.add("Germany");
		countries.add("Ghana");
		countries.add("Greece");
		countries.add("Grenada");
		countries.add("Guatemala");
		countries.add("Guinea");
		countries.add("Guinea-Bissau");
		countries.add("Guyana");
		countries.add("Haiti");
		countries.add("Honduras");
		countries.add("Hungary");
		countries.add("Iceland");
		countries.add("India");
		countries.add("Indonesia");
		countries.add("Iran");
		countries.add("Iraq");
		countries.add("Ireland");
		countries.add("Israel");
		countries.add("Italy");
		countries.add("Jamaica");
		countries.add("Japan");
		countries.add("Jordan");
		countries.add("Kazakhstan");
		countries.add("Kenya");
		countries.add("Kiribati");
		countries.add("Kosovo");
		countries.add("Kuwait");
		countries.add("Kyrgyzstan");
		countries.add("Laos");
		countries.add("Latvia");
		countries.add("Lebanon");
		countries.add("Lesotho");
		countries.add("Liberia");
		countries.add("Libya");
		countries.add("Liechtenstein");
		countries.add("Lithuania");
		countries.add("Luxembourg");
		countries.add("Macedonia (FYROM)");
		countries.add("Madagascar");
		countries.add("Malawi");
		countries.add("Malaysia");
		countries.add("Maldives");
		countries.add("Mali");
		countries.add("Malta");
		countries.add("Marshall Islands");
		countries.add("Mauritania");
		countries.add("Mauritius");
		countries.add("Mexico");
		countries.add("Micronesia");
		countries.add("Moldova");
		countries.add("Monaco");
		countries.add("Mongolia");
		countries.add("Montenegro");
		countries.add("Morocco");
		countries.add("Mozambique");
		countries.add("Myanmar (Burma)");
		countries.add("Namibia");
		countries.add("Nauru");
		countries.add("Nepal");
		countries.add("Netherlands");
		countries.add("New Zealand");
		countries.add("Nicaragua");
		countries.add("Niger");
		countries.add("Nigeria");
		countries.add("North Korea");
		countries.add("Norway");
		countries.add("Oman");
		countries.add("Pakistan");
		countries.add("Palau");
		countries.add("Palestine");
		countries.add("Panama");
		countries.add("Papua New Guinea");
		countries.add("Paraguay");
		countries.add("Peru");
		countries.add("Philippines");
		countries.add("Poland");
		countries.add("Portugal");
		countries.add("Qatar");
		countries.add("Romania");
		countries.add("Russia");
		countries.add("Rwanda");
		countries.add("Saint Kitts and Nevis");
		countries.add("Saint Lucia");
		countries.add("Saint Vincent and the Grenadines");
		countries.add("Samoa");
		countries.add("San Marino");
		countries.add("Sao Tome and Principe");
		countries.add("Saudi Arabia");
		countries.add("Senegal");
		countries.add("Serbia");
		countries.add("Seychelles");
		countries.add("Sierra Leone");
		countries.add("Singapore");
		countries.add("Slovakia");
		countries.add("Slovenia");
		countries.add("Solomon Islands");
		countries.add("Somalia");
		countries.add("South Africa");
		countries.add("South Korea");
		countries.add("South Sudan");
		countries.add("Spain");
		countries.add("Sri Lanka");
		countries.add("Sudan");
		countries.add("Suriname");
		countries.add("Swaziland");
		countries.add("Sweden");
		countries.add("Switzerland");
		countries.add("Syria");
		countries.add("Taiwan");
		countries.add("Tajikistan");
		countries.add("Tanzania");
		countries.add("Thailand");
		countries.add("Timor-Leste");
		countries.add("Togo");
		countries.add("Tonga");
		countries.add("Trinidad and Tobago");
		countries.add("Tunisia");
		countries.add("Turkey");
		countries.add("Turkmenistan");
		countries.add("Tuvalu");
		countries.add("Uganda");
		countries.add("Ukraine");
		countries.add("United Arab Emirates (UAE)");
		countries.add("United Kingdom (UK)");
		countries.add("United States of America (USA)");
		countries.add("Uruguay");
		countries.add("Uzbekistan");
		countries.add("Vanuatu");
		countries.add("Vatican City (Holy See)");
		countries.add("Venezuela");
		countries.add("Vietnam");
		countries.add("Yemen");
		countries.add("Zambia");
		countries.add("Zimbabwe");
		return countries;
	}


}
