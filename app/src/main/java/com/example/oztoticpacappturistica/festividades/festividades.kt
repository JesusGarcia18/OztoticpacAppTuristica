package com.example.oztoticpacappturistica.festividades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

class Festividades : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_festividades, container, false)

        val festividades = listOf(
            Festividad("Festival del Nahual", "Un festival dedicado al nahual protector de la Laguna de Nogales, lleno de cultura y misticisno. Se realizan eventos y actividades inclinadas al vinculo espitirtual y cultural entre la naturaleza y la vida de la comunidad.  Decenas de presentaciones de danza llenan de color esta festividad local en el recinto natural de la Laguna de Nogales, ademas de la realizacion de Bodas Prehispanicas, Limpias, Juegos de Pelota, y un espectaculo sobre el agua del Nahual de Nogales. Sin duda, un festival que alimenta la identidad de la comunidad, a principios de Marzo.", R.drawable.festival_nahual),
            Festividad("Festival de la Laguna", "Temporada de festival en la Laguna de Nogales, aprovechando la temporada vacacional de Semana Santa. Durante las dos semanas que rodean Semana Santa, el recinto ofrece eventos gratuitos donde se presentan varios artistas y grupos de música. Ademas de dichas presentaciones, se encuentran numerosos locales de comida tradicional, degustacion de bebidas y area de juegos mecanicos.",R.drawable.festival_laguna),
            Festividad("Festival de la Fresa", "En este festival nos movemos a la Localidad de Cecilio Teran, perteneciente al municipio de Nogales, productora local de la fresa, donde se realizan presentaciones culturales, gastronomicas y de acrobacias para el publico. Festejando la temporada de cosecha, el festival comparte esta abundancia con la comunidad, en el penultimo fin de semana de Abril.", R.drawable.festival_fresa),
            Festividad("Beer Fest de la Laguna", "La Laguna de Nogales vuelve a ser anfitriona, de numerosas presentaciones de cerveza y alcohol artesanal que se producen en los alrededores de las Altas Montañas y otros estados. Un evento con varios numeros musicales locales, que cubren un fin de semana de Agosto.", R.drawable.festival_beerfest),
            Festividad("Dia de Muertos", "Nogales es un municipio apegado al misticismo de nuestras raices, realizando una pequeña feria del Dia de Muertos frente a su palacion municipal. Sin embargo, lo mas llamativo de esas fechas, en una noche a visperas de la visita de nuestros difuntos, donde la avenida se llena de cientos de catrinas y catrines, velando y honrando a los que partireron, en un desfile folklorico y cultural. Tambien el Panteon Municipal se viste de color, con las visitas guiadas por el terreno santo, y las ofrendas que la comunidad hace con sus seres del descanso eterno.", R.drawable.festival_dia_muertos),
            Festividad("Laguna Iluminada", "Para la temporada navideña, Nogales se viste de colores y magia, y La Laguna de Nogales se transforma en la Villa Iluminada de Nogales, donde millones de luces dan forma a castillos y pavellones navideños, con decenas de locales gastronomicos, juegos mecanicos, presentaciones de danza y musica ambientadas en las fechas decembrinas, una Casa de Santa, y ademas una Pista de Hielo en el auditorio del Palacio Municipal. Hasta este año, Nogales ha sido el primer municipio en la region en aperturar dicha festividad de esta manera, desde el 2018. La navidad cubre de luz la Laguna durante todo el mes de Diciembre, y la primer semana de Enero.", R.drawable.laguna_iluminada)
        )
        val recyclerView: RecyclerView = view.findViewById(R.id.rvFestividades)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = FestividadesAdapter(festividades)

        return view
    }


}