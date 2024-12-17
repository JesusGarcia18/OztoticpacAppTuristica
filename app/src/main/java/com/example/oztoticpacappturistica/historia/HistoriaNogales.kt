package com.example.oztoticpacappturistica.historia

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oztoticpacappturistica.R

class HistoriaNogales : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_historia_nogales, container, false)

        val contenidoHistoria = listOf(
            ContenidoHistoria(R.drawable.glifo_nahuatl, "Antes conocida como Oztoticpac, que en náhuatl significa \"sobre la montaña\", Nogales fue un asentamiento importante durante el periodo prehispánico. Gobernada por Moctezuma Ilhuicamina, formaba parte de la cultura tolteca, y mas tarde de la mexica; se dedicaba a la agricultura, el comercio y una ganadería incipiente. Sus habitantes politeístas adoraban a diversos dioses relacionados con la naturaleza. La llegada de Hernán Cortés en 1519 marcó el inicio de un cambio drástico en su historia. Acompañado de La Malinche y posteriormente de prisioneros como Cuauhtémoc, último emperador azteca, Cortés introdujo la cultura española, que se fusionó con la indígena, dando lugar a una sociedad mestiza."),
            ContenidoHistoria(R.drawable.pueblo_indios, "Durante la época colonial, Oztoticpac cambió su nombre a Ingenio de los Nogales de San Juan Bautista, tras la introducción del cultivo de caña de azúcar por Ojeda el Tuerto, bajo ordenes de Hernán Cortés. Prosperó como una hacienda azucarera importante, pero en 1716 un incendio destruyó el ingenio. En 1721, la zona fue reconocida como Pueblo de Indios. Esta etapa marcó el inicio de su organización política y religiosa, con la iglesia de San Juan Bautista como punto central."),
            ContenidoHistoria(R.drawable.san_lorenzo_historia, "Con la Independencia de México en 1821 y la llegada del ferrocarril en 1873, Nogales vivió un auge económico conocido como la Manchester Veracruzana. Se establecieron importantes fábricas como San Lorenzo y Mirafuentes, además de un aserradero de mármol. Esta prosperidad atrajo a inmigrantes, incluidos artesanos italianos que contribuyeron al desarrollo de la región. Sin embargo, también fue escenario de conflictos laborales, como la huelga de los trabajadores textiles en 1907, reprimida violentamente por el régimen porfirista."),
            ContenidoHistoria(R.drawable.nogales_xx, "En 1910, Nogales fue elevada a la categoría de villa y en 1971 adquirió el rango de ciudad. Durante el Porfiriato, se consolidaron los tres poderes institucionales (político, económico y religioso). Nogales fue un punto de paso clave en la ruta hacia el puerto de Veracruz durante el periodo colonial. Este camino facilitaría más tarde el transporte de mercancías como azúcar y textiles. Fue un centro clave en el movimiento obrero, donde figuras como Heriberto Jara destacaron por su lucha social. Sin embargo, con el tiempo, la industria textil perdió fuerza, y para 1990, la fábrica de San Lorenzo cerró tras una huelga."),
            ContenidoHistoria(R.drawable.nogales_panom, "En la actualidad, Nogales busca recuperar su identidad y atraer turismo nacional. La administración local ha promovido eventos culturales como el Festival del Nagual, el Festival de la Fresa y el Festival de la Laguna Navideña, además de impulsar el turismo de aventura. Este esfuerzo pretende honrar las raíces históricas de la ciudad mestiza y posicionarla como un destino turístico de relevancia.")
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rvHistoria)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = HistoriaAdapter(contenidoHistoria)

        return view
    }
}