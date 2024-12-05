package databases

class Sitios (private val db: AppDatabase){

        suspend fun insertarSitios() {
            val sitios = listOf(
                Sitio(
                    nombre = "Laguna de Nogales",
                    categoria = "Naturales-Culturales",
                    informacion = "Balneario natural y recinto cultural principal de Nogales. Anfitriona de la Villa Iluminada y del festival de identidad cultural Oztoc",
                    historia = "Como su nombre lo dice, es una laguna natural formada por aguas subterraneas provenientes del deshielo del volcan Citlaltepetl, o Pico de Orizaba. Aprovechado desde los asentamientos prehispanicos para la agricultura y el abastecimiento de agua, ademas de ser un importante recinto religioso, ya que en leyendas se dice que el señor Quetzalcoatl bebio de sus aguas durante su partida al exilio. En la epoca de conquista y colonia, con la introduccion de la caña de azucar en la zona, se establece el ingenio San Juan Bautista de los Nogales, que hizo uso de sus aguas para la actividad agricultora e industrial. Tambien ofrecia en aquellos tiempos la actividad pesquera. La Laguna de Nogales es una fueste de misticistmo, ya que los mitos y las lenguas locales hablan del avistamiento de chaneques en sus alrededores, quienes se encargan de hacer travesuras a sus visitantes; de la proteccion de un legendario Nahual que en tiempos de la colonia protegio la alteracion de esta joya natural y se opuso ante obreros y constructores; del rumor sobre sus habitantes hadas en los rincones de sus arboles, quienes perciben las vibras positivas de la gente y les conseden salud o buenos deseos. Actualmente es anfitriona de la Vlla Iluminada en la temporada navideña; del festival de identidad cultural Oztoc, dedicado al protector Nahual, y de su propio Festival de la Laguna en temporada de Semana Santa.",
                    costos = "Acceso completeamente gratis a sus areas naturales y balneario.", //or null
                    horarios = "Aunque sus puertas estan abiertas las 24 horas, se recomienda el acceso en un horario de 6:00 a 21:00 horas.",
                    menu = "", //or null
                    servicios = "Museos, locales gastronomicos, bares, sanitarios, paseos en lancha. (Se pueden aplicar costos independientes)",
                    contactos = "", //or null
                    nombreContacto = "",
                    latitude = 18.820646,
                    longitude = -97.164477
                ),
                        Sitio (
                            nombre = "Sitio Turistico de Relajacion OZTOTIPAC",
                            categoria = "Naturales-Culturales",
                            informacion = "Rincon de relajacion, con metodos herbolaricos y naturales.",
                            costos = "",
                            horarios = "Abierto entre las 9:00 y las 17:00 horas.",
                            menu = "",
                            servicios = "Ofrece serivio de Baño de Vapor, Masajes, Sauna y Cafeteria Tradicional",
                            contactos = "",
                            nombreContacto = "",
                            latitude = 18.820392,
                            longitude = -97.165617
                            ),
                Sitio(
                    nombre = "Sendero Cerro de la Capilla",
                    categoria = "Naturales-Culturales",
                    informacion = "Un encantador sendero que combina naturaleza y tradición, ideal para caminatas con vistas espectaculares.",
                    historia = "El Sendero del Cerro de la Capilla es más que un camino; es una experiencia que conecta a sus visitantes con las raíces espirituales de Nogales. Este sendero lleva a una antigua capilla situada en lo alto del cerro, un lugar que por años ha sido centro de peregrinaciones y celebraciones locales. A lo largo del trayecto, encontrarás cruces y estaciones que marcan la ruta, reflejando la importancia religiosa y cultural de este recorrido. Además de su valor espiritual, el sendero ofrece vistas impresionantes del municipio, lo que lo convierte en un sitio imperdible para locales y turistas.",
                    costos = "El acceso al sendero es gratuito, aunque algunos eventos organizados pueden tener costo adicional.",
                    horarios = "Abierto al público todo el día, pero se recomienda recorrerlo durante las mañanas o tardes para evitar el sol intenso.",
                    servicios = "El sendero no cuenta con servicios formales. Es recomendable llevar agua, protector solar y calzado cómodo para disfrutar del recorrido.",
                    contactos = "",
                    nombreContacto = "",
                    latitude = 18.819602,
                    longitude = -97.165242
                ),
                    Sitio(
                        nombre = "Cañon de la Carbonera",
                        categoria = "Naturales-Culturales",
                        informacion = "Un cañón natural rodeado de impresionantes formaciones rocosas, ideal para caminatas y contacto con la naturaleza.",
                        historia = "El Cañón de la Carbonera ha sido un lugar emblemático para los habitantes de Nogales, conocido por su biodiversidad y sus vistas únicas. Su nombre proviene de la antigua tradición de recolectar carbón en la zona. En sus senderos, puedes encontrar vestigios de la vida rural que en su tiempo floreció aquí, y muchas leyendas han surgido alrededor de sus formaciones naturales, como la Piedra del Águila, que se alza majestuosamente como guardiana del lugar. Tambien se puede admirar los Hilitos de Plata, cascadas provienentes de la zona Este del cañon que reflejan un brillo plateado con la luz del Sol",
                        costos = "Visitar el Cañón de la Carbonera es gratuito. Algunos guías locales ofrecen recorridos por propinas o tarifas accesibles.",
                        horarios = "Puedes visitarlo en cualquier momento, pero se recomienda hacerlo durante el día para disfrutar del paisaje y evitar riesgos en los senderos.",
                        servicios = "No cuenta con servicios formales, pero en los alrededores es posible encontrar áreas para picnic. Lleva tu basura contigo para mantener el lugar limpio.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.832709,
                        longitude = -97.175229

                        ),
                    Sitio(
                        nombre = "Cueva de Marmol",
                        categoria = "Naturales-Culturales",
                        informacion = "Un fascinante rincón natural con formaciones de mármol únicas y un aire de misterio.",
                        historia = "La Cueva del Mármol es un tesoro escondido de Nogales, conocida por sus espectaculares formaciones naturales que brillan como mármol bajo la luz. Este lugar ha sido objeto de historias y leyendas, algunas relacionadas con tesoros escondidos y encuentros misteriosos en su interior. Además de su valor geológico, la cueva ha sido explorada por generaciones, siendo un sitio de interés tanto para aventureros como para quienes buscan un momento de tranquilidad rodeados por la belleza de la naturaleza.",
                        costos = "La entrada a La Cueva del Mármol es gratuita. Algunos guías locales ofrecen recorridos con tarifas accesibles.",
                        horarios = "Acceso disponible todo el día, aunque es recomendable visitarla durante las horas de luz para una mejor experiencia.",
                        servicios = "No cuenta con servicios establecidos, por lo que es aconsejable llevar equipo básico, como linternas y calzado adecuado para exploración.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.834546,
                        longitude = -97.168241

                        ),
                    Sitio(
                        nombre = "Mirador y Sendero Palo Verde",
                        categoria = "Naturales-Culturales",
                        informacion = "Un pintoresco sendero que conecta con el Cañón de la Carbonera y culmina en un mirador con vistas espectaculares.",
                        historia = "El Sendero de Palo Verde es una extensión del recorrido por el Cañón de la Carbonera, diseñado para los aventureros que desean explorar más a fondo la belleza de la región. Este sendero serpentea a través de áreas de densa vegetación y termina en el Mirador de Palo Verde, un punto privilegiado para contemplar la inmensidad del cañón y los paisajes de Nogales. Antiguamente, este mirador fue utilizado por los habitantes como un punto de observación para protegerse de posibles amenazas, y ahora es un lugar de paz y reflexión para quienes lo visitan.",
                        costos = "El acceso al sendero y al mirador es gratuito.",
                        horarios = "Se puede recorrer a cualquier hora del día, aunque se sugiere hacerlo por la mañana o al atardecer para aprovechar las mejores vistas y evitar el calor del mediodía.",
                        servicios = "Este recorrido no cuenta con servicios. Se recomienda llevar agua, protector solar y zapatos cómodos, además de una cámara para capturar los paisajes.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.838706,
                        longitude = -97.194055

                        ),
                    Sitio(
                        nombre = "Mirador Piedra del Aguila",
                        categoria = "Naturales-Culturales",
                        informacion = "Mirador natural en el Cañón de La Carbonera, ideal para actividades al aire libre.",
                        historia = "La Piedra del Águila se alza como un mirador icónico en Nogales, Veracruz, a más de 130 metros sobre el Cañón de La Carbonera. Su forma distintiva, similar a la cabeza de un águila en vuelo, le da su nombre. Parte del sendero ecoturístico Palo Verde, este sitio conecta con otros atractivos de la región, incluyendo vistas hacia el majestuoso Pico de Orizaba.\n" +
                                "\n" +
                                "La comunidad local ha gestionado el sitio, fomentando su uso como destino para el turismo de naturaleza y actividades como el rapel, el senderismo y la observación de fauna. Equipos para actividades extremas suelen estar disponibles en la zona, lo que convierte a este mirador en un punto de interés para aventureros y ecoturistas.",
                        costos = "Pueden aplicar tarifas por equipo o servicios de guía, sin embargo la visita al sitio es gratuito.",
                        horarios = "Acceso durante el día; para actividades guiadas, es mejor coordinarse con las autoridades locales.",
                        servicios = "Actividades de senderismo, observación de la naturaleza y rapel.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.852097,
                        longitude = -97.195442
                        ),
                    Sitio(
                        nombre = "Sendero al Balcon del Diablo",
                        categoria = "Naturales-Culturales",
                        informacion = "Sendero natural que lleva al impresionante mirador conocido como el Balcón del Diablo.",
                        historia = "Este sendero, ubicado en una zona montañosa de Nogales, Veracruz, conduce a un mirador con vistas impactantes de los alrededores. Es un destino favorito para excursionistas y amantes de la naturaleza por su acceso a paisajes únicos. En el trayecto, se pueden apreciar formaciones rocosas y flora autóctona, enriqueciendo la experiencia de contacto con la naturaleza. Este lugar es ideal para actividades de ecoturismo, combinando aventura y exploración.",
                        costos = "Acceso libre.", //or null
                        horarios = "Sin horario limite, pero se recomienda visitar durante las horas de luz para disfrutar de la panoramica.",
                        servicios = "Guías disponibles bajo solicitud previa.",
                        contactos = "", //or null
                        nombreContacto = "",
                        latitude = 18.853767,
                        longitude = -97.196338
                ),
                    Sitio(
                        nombre = "Ojo de Agua del Encinar",
                        categoria = "Naturales-Culturales",
                        informacion = "El Ojo de Agua de El Encinar es un balneario natural caracterizado por sus aguas cristalinas, perfecto para disfrutar un día en contacto con la naturaleza.",
                        historia = "Este manantial natural tiene una larga historia como punto de recreación en la región. Su nombre, \"Ojo de Agua,\" resalta su carácter como fuente de agua natural. La zona es muy apreciada tanto por locales como por turistas que buscan relajarse en un ambiente sereno y disfrutar del contacto con la naturaleza. Su agua se considera relajante y terapéutica, lo que lo convierte en un destino especial para los visitantes. La preservación del sitio es manejada por la comunidad local, quienes mantienen el espacio limpio y bien cuidado.",
                        costos = "Entrada Gratuita", //or null
                        horarios = "Abierto en un horario de las 8:00 a las 18:00 horas.",
                        servicios = "Ideal como zona de descanso, area para nadar, y cuenta con espacios de sombras naturales.",
                        contactos = "", //or null
                        nombreContacto = "",
                        latitude = 18.818361,
                        longitude = -97.196370
                        ),
                    Sitio(
                        nombre = "Rincon de las Doncellas",
                        categoria = "Naturales-Culturales",
                        informacion = "En lo profundo de Nogales, Veracruz, se encuentra el mágico Rincón de las Doncellas, un lugar donde la naturaleza y las leyendas convergen. Aquí, puedes desconectarte del ruido y disfrutar de un rincón lleno de calma y encanto.",
                        historia = "El Rincón de las Doncellas no solo es conocido por su belleza natural, sino también por las historias que envuelven el lugar. Según las leyendas locales, este rincón fue un espacio de reunión para jóvenes doncellas, quienes buscaban refugio en la tranquilidad de la naturaleza. Los lugareños aún cuentan que las aguas cristalinas de este rincón tienen un misticismo especial, y su entorno verde invita a la contemplación y al descanso. Más que un sitio, es una ventana al pasado, donde la historia oral se mezcla con la fascinación de lo desconocido.",
                        costos = "Entrada gratuita a todo el publico.",
                        horarios = "Horario de 7:00 a 17:00 horas.",
                        servicios = "Cuenta con senderos natruales para recorrido, zonas de descanso y picnic, locales de consumo gastronomico, y area de natación.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.793697,
                        longitude = -97.197740,
                        ),
                    Sitio(
                        nombre = "Cueva del Diablo",
                        categoria = "Naturales-Culturales",
                        informacion = "Atrévete a explorar La Cueva del Diablo, un sitio enigmático en Nogales, Veracruz, que te transportará a un mundo de misterio y leyendas. Es un destino único para los amantes de la aventura y la naturaleza.",
                        historia = "La Cueva del Diablo es conocida por su fascinante historia y las leyendas que la rodean. Se dice que este lugar, escondido entre la vegetación, era utilizado en el pasado por aventureros y exploradores locales. Las historias locales narran que su nombre proviene de un mito en el que el diablo se manifestaba en este sitio para retar a quienes se atrevían a entrar. Hoy en día, es un lugar que combina el encanto de la naturaleza con un toque de misterio, haciendo que cada visitante sienta la emoción de descubrir algo extraordinario. Además, su entorno ofrece una vista impresionante de las montañas circundantes.",
                        costos = "Entrada Gratuita",
                        horarios = "Abierto todos los dias, en un horario de 8:00 a 17:00 horas. (Varia por festividades o clima)",
                        servicios = "Cuenta con guías locales disponibles para recorridos, senderos seguros hacia la cueva y zona para descanso y fotografía.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.791071,
                        longitude = -97.191756,

                        ),
                    Sitio(
                        nombre = "Taza de Agua",
                        categoria = "Naturales-Culturales",
                        informacion = "Descubre la Taza de Agua, un encantador balneario natural en Nogales, Veracruz, donde la frescura de sus aguas cristalinas te invita a relajarte y disfrutar de la tranquilidad que solo la naturaleza puede ofrecer.",
                        historia = "La Taza de Agua ha sido un punto de encuentro tradicional para las familias de la región desde hace generaciones. Su peculiar nombre proviene de la forma redondeada de su poza principal, que recuerda a una taza rebosante de agua clara. Este lugar ha sido cuidado con esmero por la comunidad local, asegurando su conservación como un espacio ideal para convivir y desconectar del ritmo cotidiano. Las aguas del sitio son conocidas por ser frescas y revitalizantes, atrayendo a visitantes que buscan un refugio en medio de la naturaleza.",
                        costos = "Entrada gratuita.",
                        horarios = "Abierto todos los días de 8:00 a 18:00 horas.",
                        servicios = "Cuenta con área para nadar en aguas naturales, espacios para descansar bajo la sombra de árboles y zona para picnic.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.777050,
                        longitude = -97.204579,

                        ),
                    Sitio(
                        nombre = "Paseo de los Ahuehuetes",
                        categoria = "Naturales-Culturales",
                        informacion = "El Paseo de los Ahuehuetes es un hermoso corredor natural bordeado por imponentes ahuehuetes, conocido como el \"árbol nacional de México\". Es ideal para paseos tranquilos y conexión con la naturaleza.",
                        historia = "Este lugar recibe su nombre por los majestuosos ahuehuetes que lo enmarcan, algunos con más de 500 años de antigüedad. En la época prehispánica, los ahuehuetes tenían un simbolismo sagrado, y los pueblos originarios los consideraban guardianes del agua. Hoy, el Paseo de los Ahuehuetes es un sitio donde las familias y visitantes disfrutan caminatas y momentos de relajación, además de aprender sobre la riqueza natural de Nogales. Su conservación es una prioridad para la comunidad, quienes lo ven como un pulmón verde y un legado histórico invaluable.",
                        costos = "Entrada Gratuita.",
                        horarios = "Abierto todos los días de 8:00 a 20:00 horas.",
                        servicios = "Cuenta con senderos para caminar y disfrutar del paisaje, bancas y áreas de descanso, ademas de señalizaciones informativas sobre los ahuehuetes.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.789134,
                        longitude = -97.197081,

                        ),
                    Sitio(
                        nombre = "Cañon del Rio Blanco",
                        informacion = "Adéntrate en el impresionante Cañón del Río Blanco, una maravilla natural que atraviesa varios municipios de Veracruz, incluido Nogales. Este lugar combina paisajes espectaculares, biodiversidad y un fuerte sentido de conexión con la naturaleza.",
                        categoria = "Naturales-Culturales",
                        historia = "El Cañón del Río Blanco es parte de un área natural protegida que abarca aproximadamente 45,000 hectáreas, rica en flora y fauna endémica. Este cañón es el testigo silencioso de miles de años de historia, con el río Blanco como su corazón palpitante. En su recorrido, el río ha tallado paredes imponentes, creando un refugio para muchas especies. En el caso de Nogales, el cañón ofrece vistas inigualables y espacios ideales para senderismo y contemplación. Este lugar no solo es un atractivo turístico, sino también una fuente vital para las comunidades locales que lo rodean, quienes lo cuidan como un tesoro natural.",
                        horarios = "",
                        servicios = "Cuenta con senderos para caminatas y exploración, miradores naturales con vistas espectaculares, y guías locales disponibles para recorridos informativos.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.798457,
                        longitude = -97.205433,

                        ),
                    Sitio(
                        nombre = "Parroquia de San Isidro Labrador",
                        categoria = "Historicos",
                        informacion = "La Parroquia de San Isidro Labrador es el corazón espiritual de Nogales, un lugar lleno de devoción y tradición, rodeado de vestigios arquitectónicos que narran la historia de su pasado como parte de la ex Hacienda El Encinar.",
                        historia = "Esta parroquia, construida en honor a San Isidro Labrador, está profundamente vinculada con la historia agrícola y cultural de Nogales. El terreno donde se encuentra formaba parte de la antigua Hacienda El Encinar, una de las más importantes en la región durante el auge cafetalero y cañero. A su alrededor aún pueden observarse restos de la hacienda, como viejas estructuras de piedra y caminos empedrados que conectaban las áreas de cultivo con las zonas habitacionales.\n" +
                                "\n" +
                                "La parroquia refleja un estilo colonial sobrio, con toques tradicionales que evocan la sencillez de la vida rural de antaño. En su interior, imágenes religiosas y detalles arquitectónicos preservan la esencia histórica del lugar. Este espacio no solo es un símbolo de fe, sino también un recordatorio de la rica herencia cultural de la región.",
                        costos = "Abierto a todo el publico.",
                        horarios = "Con horarios de Lunes a sábado, de 7:00 a 19:00 horas; y Domingos de 7:00 a 20:00 horas.",
                        servicios = "Cuenta con celebración de misas diarias, área de reflexión y oración, eventos religiosos y culturales durante las festividades patronales.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.813328,
                        longitude = -97.194795,

                        ),
                    Sitio(
                        nombre = "Parroquia de San Juan Bautista",
                        categoria = "Historicos",
                        informacion = "La Parroquia de San Juan Bautista es un referente histórico y religioso en el municipio de Nogales, Veracruz. Este templo ha sido testigo de importantes momentos históricos y sigue siendo un centro de encuentro para los feligreses y la comunidad en general.",
                        historia = "La Parroquia de San Juan Bautista tiene una larga historia que data de la época colonial, aunque su estructura actual refleja varias etapas de remodelación y ampliación. Situada en el centro del municipio, la iglesia ha sido un importante punto de referencia para los habitantes de Nogales. La iglesia fue construida sobre un terreno que, en sus orígenes, también albergó una antigua capilla dedicada a San Juan Bautista. Con el paso de los años, la iglesia se expandió y se mejoró, transformándose en el importante centro religioso que es hoy.\n" +
                                "\n" +
                                "El edificio tiene un estilo arquitectónico que fusiona elementos coloniales con detalles más recientes. La fachada principal está adornada con detalles de la época virreinal, mientras que el interior se destaca por sus vitrales coloridos y su altar mayor, que rinde homenaje a San Juan Bautista, patrón de la localidad. Además, la parroquia está rodeada de calles que aún conservan el trazado original de la ciudad, lo que hace que el entorno de la iglesia también sea parte importante de su historia.",
                        costos = "Entrada gratuita.",
                        horarios = "Accesible en un horario de Lunes a Sabado, de 7:00 a 19:00 horas, y Domingo de 7:00 a 20:00 horas.",
                        servicios = "Realiza celebracion de misas diarias, cuenta con visitas turisticas y religiosas, ademas de realizar eventos especiales durante las festividades patronales.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.822170,
                        longitude = -97.162675,

                        ),
                    Sitio(
                        nombre = "Ex Fabrica \"San Lorenzo\"",
                        categoria = "Historicos",
                        informacion = "La Ex Fábrica Textil San Lorenzo, un símbolo del auge industrial de Nogales, Veracruz, evoca la historia de una época en que la región floreció gracias a su producción textil. Este lugar guarda un misticismo único entre las ruinas de sus estructuras.",
                        historia = "Fundada en el siglo XIX, la Fábrica Textil San Lorenzo fue una de las empresas más importantes de la región durante el auge de la industria textil en México. Aprovechando las corrientes del río Blanco, que pasa por la zona, la fábrica utilizaba un sistema hidráulico para operar su maquinaria. Esto convirtió a la fábrica en un referente innovador en su tiempo, ofreciendo empleo a gran parte de la población local y atrayendo familias de distintas partes del país.\n" +
                                "\n" +
                                "Con el tiempo, y tras el declive de la industria textil en la región, la fábrica cerró sus puertas, dejando sus instalaciones en ruinas. Sin embargo, el sitio sigue siendo un recordatorio palpable del pasado industrial de Nogales, con estructuras que conservan detalles de la época, como arcos de ladrillo, enormes ventanas y chimeneas de piedra. Hoy en día, el lugar es un atractivo tanto para los amantes de la historia como para quienes disfrutan de la exploración de sitios abandonados con valor histórico.",
                        costos = "No hay costo de acceso, sin embargo cuenta como predio privado, solo puede ser visitado por sus exteriores.",
                        horarios = "",
                        servicios = "",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.816083,
                        longitude = -97.164697,

                        ),
                    Sitio(
                        nombre = "Tuneles Ferreos de la Carbonera (Rosecranz)",
                        categoria = "Historicos",
                        informacion = "Adéntrate en un pasaje histórico fascinante con los Túneles Ferreos de la Carbonera, una obra de ingeniería que guarda los ecos del desarrollo ferroviario y las dinámicas industriales del siglo pasado en la región de Nogales, Veracruz.",
                        historia = "Construidos durante el auge del ferrocarril en México en el siglo XIX, los Túneles Ferreos de la Carbonera forman parte de la infraestructura que conectaba las principales industrias textiles de la región con el resto del país. Su nombre alternativo, \"Rosecranz,\" proviene del ingeniero que lideró parte de las obras para el trazado de esta red.\n" +
                                "\n" +
                                "Estos túneles fueron diseñados para facilitar el paso del tren a través de la accidentada geografía del Cañón de la Carbonera, aprovechando la inclinación natural del terreno. Su construcción requirió la excavación de roca sólida y la colocación de refuerzos de acero y ladrillo, lo que muestra la avanzada técnica utilizada para la época.\n" +
                                "\n" +
                                "Actualmente, los túneles permanecen como un vestigio del desarrollo industrial y ferroviario de la región. Rodeados de un entorno natural exuberante, los túneles han sido testigos del paso del tiempo y se han convertido en un punto de interés para exploradores y turistas.",
                        costos = "Entrada gratuita, por el Mirador de Palo Verde o el sendero del Rio de la Carbonera.",
                        horarios = "Accesible todo el tiempo, se recomienda visitar por las mañanas o tardes para evitar el maximo calor del medio dia, y disfrutar del sendero.",
                        servicios = "Zona apta para senderismo y fotografia historica o de paisajes.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.853168,
                        longitude = -97.196271,

                        ),
                    Sitio(
                        nombre = "Antigua Ruta del Ferrocarril",
                        categoria = "Historicos",
                        informacion = "La Antigua Ruta del Ferrocarril, que corre paralela a la Autopista México-Veracruz, conecta las localidades de El Encinar y Barrientos. Este camino es una joya histórica, rodeada por un impresionante paisaje montañoso, ideal para los amantes del senderismo y la exploración.",
                        historia = "Construida en el siglo XIX, esta ruta fue parte del sistema ferroviario diseñado para transportar bienes y pasajeros entre las zonas industriales de Nogales y otras ciudades importantes. El ferrocarril desempeñó un papel crucial en el desarrollo económico de la región, transportando productos textiles desde fábricas como San Lorenzo y Río Blanco hacia mercados más amplios.\n" +
                                "\n" +
                                "Aunque actualmente ya no opera como vía férrea, los restos de los rieles y la infraestructura circundante permanecen como testigos de su glorioso pasado. Durante el recorrido, se pueden observar antiguos puentes ferroviarios, túneles y estaciones que alguna vez estuvieron llenas de actividad.\n" +
                                "\n" +
                                "Hoy, la ruta es utilizada como sendero recreativo y ofrece una conexión única con la naturaleza y la historia local, siendo un atractivo especial para excursionistas y ciclistas.",
                        costos = "Acceso gartuito.",
                        horarios = "Abierto todo el dia (se pueden realizar eventos programados)",
                        servicios = "Cuenta como zona de senderismo, ciclismo, y area de fotografia hsitorica.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.818216,
                        longitude = -97.227144,

                        ),
                    Sitio(
                        nombre = "Xipatlani",
                        categoria = "Gastronomicos",
                        informacion = "Restaurante de comida tradicional mexicana con toque contemporaneo que te invita a descubrir la riqueza cultural y culinaria de la región.",
                        historia = "Xipatlani toma su nombre de la palabra náhuatl que significa \"rebosar\" o \"abundancia\", reflejando su filosofía de ofrecer generosidad en sabores y experiencias. Inaugurado como un proyecto familiar, el restaurante se ha convertido en un referente local, destacándose por su compromiso con ingredientes frescos y platillos que rinden homenaje a las raíces de la región.\n" +
                                "\n" +
                                "Su menú incluye recetas tradicionales que han pasado de generación en generación, reinventadas para sorprender a los paladares modernos. Además, su decoración celebra la herencia cultural de Nogales, con detalles artesanales y fotografías históricas.",
                        menu = "",
                        costos = "El costo basico es de $70.00 pesos por persona, con tres tiempos. Las especialidades del dia varian entre los $60.00 y $150.00 pesos por persona. Ademas de contar con desayunos y paquetes de comida corrida que rondan en precio alrededor de los $70.00 pesos.",
                        horarios = "Abierto todos los dias, de 9:00 a 18:00 horas (Varia por festividades). ",
                        servicios = "Ofrece comida rapida, servicio de tres tiempos, y heladeria.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.818947,
                        longitude = -97.161980,

                        ),
                    Sitio(
                        nombre = "Los Portales de Nogales",
                        categoria = "Gastronomicos",
                        informacion = "Los Portales de Nogales es un restaurante que destaca por su ambiente tradicional y su delicioso menú inspirado en la cocina veracruzana y mexicana. Este lugar, situado en el corazón de Nogales, es ideal para disfrutar de una comida típica mientras se aprecia la vida cotidiana de este pintoresco municipio.",
                        historia = "Los Portales de Nogales ha sido un punto de encuentro para locales y visitantes. Su nombre rinde homenaje a la arquitectura característica del parque central de Nogales, recreando el ambiente acogedor de los portales históricos de la región. El restaurante ha preservado el encanto del municipio, fusionando recetas familiares con un toque casero que evoca la nostalgia de la cocina de antaño.",
                        menu = "",
                        costos = "El costo de platillos y entradas son alrededor de $40.00 a $100.00 pesos. Paquetes de desayunos que rondan entre los $120.00 a $150.00 pesos.",
                        horarios = "Abierto todos los dias, entre las 9:00 a las 22:00 horas (Varia por festividades).",
                        servicios = "Servicio de desayuno, bar, comida al aire libre, pequeñas presentaciones artisticas y pequeños desayunos programados para ocaciones especiales (Costos extra).",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.821796,
                        longitude = -97.162692,

                        ),
                    Sitio(
                        nombre = "La Pizza",
                        categoria = "Gastronomicos",
                        informacion = "La Pizza es un restaurante local que combina el sabor de las auténticas pizzas italianas con un toque personal de la región. Es el lugar ideal para disfrutar de una comida informal, perfecta para compartir en familia o con amigos.",
                        historia = "Inaugurado como un emprendimiento familiar, La Pizza ha crecido hasta convertirse en un favorito de los habitantes de Nogales. Con recetas cuidadosamente elaboradas, su éxito se basa en la calidad de sus ingredientes y en ofrecer un ambiente cálido y relajado. Es especialmente conocido por su horno de piedra, donde las pizzas adquieren ese característico sabor ahumado.",
                        menu = "El menu general de La Pizza es:\n" + "Pizzas clásicas: Margarita, Pepperoni, Hawaiana.\n" +
                                "Pizzas especiales: Mexicana (chorizo, jalapeño, frijoles), Nogales Supreme (con ingredientes locales como queso fresco y aguacate).\n" +
                                "Complementos: Alitas BBQ, pan de ajo con queso, ensaladas frescas.\n" +
                                "Bebidas: Refrescos, cervezas artesanales y malteadas.",
                        costos = "Los precios regulares son:\n" + "Pizzas medianas: \$120 - \$180 MXN.\n" +
                                "Pizzas familiares: \$200 - \$300 MXN.\n" +
                                "Complementos: \$50 - \$120 MXN.",
                        horarios = "Abierto de Miercoles a Lunes, en un horario de 14:00 a 21:30 horas.",
                        servicios = "Ofrece servicio de comida rapida, envio a domicilio, y bar.",
                        contactos = "Servicio a Domicilio La Pizza:",
                        nombreContacto = "WhatsApp: 272 119 4404\n" + "Local: 272 171 1195",
                        latitude = 18.821760,
                        longitude = -97.162661,

                        ),
                    Sitio(
                        nombre = "Campestre Yecapixtla",
                        categoria = "Gastronomicos",
                        informacion = "El Restaurant Campestre Yecapixtla ofrece una experiencia culinaria única que combina la calidez del ambiente campestre con los sabores auténticos de la cocina mexicana, especializada en cecina y platillos tradicionales.",
                        historia = "Este restaurante toma su nombre de la región de Yecapixtla, famosa por su cecina, y trae su esencia a Nogales. Fundado hace más de 15 años, este lugar se ha posicionado como un favorito para quienes buscan disfrutar de una comida en contacto con la naturaleza, lejos del bullicio de la ciudad. Su estilo rústico y atención familiar lo convierten en un sitio acogedor para locales y visitantes.",
                        menu = "En el menu se incluye: \n" + "Especialidad: Cecina estilo Yecapixtla, acompañada de frijoles refritos, nopales y tortillas hechas a mano.\n" +
                                "Platillos regionales: Mole poblano, barbacoa de res.\n" +
                                "Postres: Dulce de calabaza, arroz con leche, flan napolitano.\n" + "Bebidas: Cervezas, Aguas frescas, Toritos.",
                        costos = "Los costos de platillos rondan entre los \$70.00 y \$200.00 pesos. Los costos de las bebidas rondan entre los \$30.00 y \$70.00 pesos. Los costos de paquetes de carnes pueden rondar entre los \$400.00 y \$600.00 pesos.",
                        horarios = "Abierto todos los dias, en un horario de 9:00 a 18:30 horas.",
                        servicios = "En general, ofrece servicio de comida en el lugar, comida para llevar, y bar.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.787305,
                        longitude = -97.199145,

                        ),
                    Sitio(
                        nombre = "Chicahuaxtla Bosque Encantado",
                        categoria = "Gastronomicos",
                        informacion = "Chicahuaxtla Bosque Encantado es un restaurante que combina un entorno natural y relajante con una cocina inspirada en sabores regionales y tradicionales. Es un destino ideal para quienes buscan disfrutar de una buena comida mientras se rodean de la magia del bosque.",
                        historia = "Ubicado en las faldas del bosque de Nogales, Chicahuaxtla se ha consolidado como un lugar para conectar con la naturaleza mientras se deleitan los sentidos. Con su nombre inspirado en la palabra náhuatl \"Chicahuaxtla\", que significa \"lugar fuerte o robusto\", el restaurante promueve la conservación del entorno y la cultura local. Desde su apertura, ha sido un punto de encuentro para familias, excursionistas y turistas que buscan una experiencia gastronómica única.",
                        menu = "En el menu podemos encontrar:\n" + "Especialidades de la casa:\n" +
                                "Trucha fresca en salsa de almendras.\n" +
                                "Caldo de hongos silvestres recolectados en la región.\n" +
                                "Enchiladas verdes con queso de cabra.\n" +
                                "Complementos:\n" +
                                "Pan artesanal horneado al día.\n" +
                                "Café orgánico de productores locales.\n" +
                                "Dulces típicos como tamales de dulce y atole.",
                        costos = "Los costos de los platillos pueden rondar entre los \$60.00 a \$250.00 pesos, las bebidas y postres entre los \$30.00 a \$80.00 pesos.",
                        horarios = "Abierto sabados y domingos, en un horario de 9:00 a 17:00 horas.",
                        servicios = "Cuenta con servicio de restaurante al aire libre.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.854854,
                        longitude = -97.212309,

                        ),
                    Sitio(
                        nombre = "Balneario Relax",
                        categoria = "Recreativos",
                        informacion = "El Balneario Relax es un espacio diseñado para disfrutar momentos de tranquilidad en un entorno natural. Ideal para familias y amigos, ofrece diversas opciones de entretenimiento acuático en un ambiente seguro y relajado.",
                        historia = "Fundado como un proyecto familiar, este balneario fue concebido con la idea de proporcionar un lugar accesible donde las personas pudieran escapar del estrés diario. Su desarrollo ha promovido el turismo local en Nogales y se ha convertido en un favorito de los visitantes, especialmente durante temporadas cálidas y festivas.",
                        costos = "La entrada tiene un precio de \$80.00 pesos para adultos y \$50.00 pesos para niños. Existen promociones para 10 personas. Tambien se rentan asadores y areas privadas por alrededor de \$400.00 pesos.",
                        horarios = "Abierto de Miercoles a Domingo, en un horario de 12:00 a 19:00 horas. ",
                        servicios = "Cuenta con vestidores, bar, toboganes, regaderas, area de asadores, mesas bajo palapas, y venta de antojitos.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.788631,
                        longitude = -97.197843,

                        ),
                    Sitio(
                        nombre = "El Eden",
                        categoria = "Recreativos",
                        informacion = "El Edén es un centro recreativo que combina la belleza natural con instalaciones para el entretenimiento familiar. Su ambiente rodeado de vegetación lo convierte en un lugar perfecto para relajarse y disfrutar de un día diferente en Nogales, Veracruz.",
                        historia = "Desde su apertura, El Edén ha buscado ser un espacio de encuentro para la comunidad, fomentando la convivencia y el turismo en la región. Su nombre hace referencia a la idea de un \"paraíso terrenal\", y los visitantes lo encuentran como un lugar ideal para escapar del ritmo de la vida cotidiana.",
                        costos = "El acceso tiene un costo de $70.00 pesos por adulto, y $40.00 pesos por niño. Tambien hay un paquete de $200.00 pesos por 4 personas.",
                        horarios = "Abierto de Miercoles a Domingo, en un horario de 10:00 a 18:00 horas.",
                        servicios = "Cuenta con alberca de agua de manantial, areas verdes para picnic o acampar, zona de area deportiva y asaderos.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.787324,
                        longitude = -97.199241,

                        ),
                    Sitio(
                        nombre = "Campo Deportivo \"Famosa\"",
                        categoria = "Recreativos",
                        informacion = "El Campo Deportivo Famosa es un espacio abierto destinado para actividades deportivas y recreativas. Con amplias instalaciones, es un lugar clave para los habitantes y visitantes que buscan practicar deportes o realizar eventos al aire libre en Nogales.",
                        historia = "Este campo se construyó como una iniciativa comunitaria para fomentar el deporte y la sana convivencia. A lo largo de los años, ha sido sede de innumerables torneos locales de fútbol y otras disciplinas, consolidándose como un punto importante para el esparcimiento y las actividades familiares.",
                        costos = "Entrada gratuita para todo el publico.",
                        horarios = "Abierto todos los dias, en un horario de 7:00 a 22:00 horas.",
                        servicios = "Cuenta con campos de futbol, area de juegos infantiles, canchas de basquetbol, y zona de gradas.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.822449,
                        longitude = -97.159153,

                        ),
                    Sitio(
                        nombre = "Hotel Boutique \"El Mesón de Samaria\"",
                        categoria = "hoteles",
                        informacion = "El Mesón de Samaria es un acogedor hotel en Nogales que combina tradición y comodidad.",
                        historia = "Diseñado para brindar una experiencia cálida y relajante, este lugar es ideal tanto para turistas que buscan explorar la región como para quienes necesitan una pausa en su viaje. Establecido hace más de dos décadas, este hotel nació como un proyecto familiar con la intención de ofrecer hospedaje de calidad en Nogales. Su diseño rústico y encantador refleja la arquitectura tradicional de la región, manteniendo un enfoque en el servicio personalizado para cada huésped.",
                        costos = "Precios de hospedaje:\n" + "Habitación estándar: Desde \$600 MXN por noche.\n" +
                                "Habitación doble o familiar: Desde \$1,000 MXN por noche.\n" +
                                "Desayuno incluido en algunas tarifas.",
                        horarios = "Recepcion las 24 horas, check-in a partir de las 14:00 horas, check-out hasta las 12:00 horas.",
                        servicios = "En sus servicios cuenta con:\n" + "Habitaciones cómodas con estilos rústicos y modernos.\n" +
                                "Servicio de restaurante con platillos regionales e internacionales.\n" +
                                "Wi-Fi gratuito en todas las instalaciones.\n" +
                                "Estacionamiento privado y seguro.\n" +
                                "Espacios para reuniones o eventos pequeños.",
                        nombreContacto = "Para reservaciones en el Mesón de Samaria:",
                        contactos = "272 175 7844",
                        latitude = 18.825458,
                        longitude = -97.161311,
                    ),
                    Sitio(
                        nombre = "Laguna Real de Nogales",
                        categoria = "hoteles",
                        informacion = "El Hotel Laguna Real ofrece una experiencia de hospedaje rodeada de tranquilidad y naturaleza, ideal para quienes desean desconectarse del bullicio y disfrutar de un entorno relajante en Nogales.",
                        historia = "Su proximidad a la Laguna de Nogales lo convierte en una excelente opción para los amantes de la naturaleza. Este hotel fue fundado como parte de un proyecto para promover el turismo ecológico y sostenible en la región. A lo largo de los años, ha recibido tanto a viajeros nacionales como internacionales que buscan un lugar cómodo y rodeado de paisajes naturales.",
                        costos = "Precios de hospedaje:\n" + "Habitación estándar: Desde \$700 MXN por noche.\n" +
                                "Suite con vista a la laguna: Desde \$1,200 MXN por noche.\n" +
                                "Paquetes turísticos: Hospedaje y actividades desde \$1,800 MXN.",
                        horarios = "Recepcion las 24 horas, check-in a partir de las 15:00 horas, check-out hasta las 11:00 horas.",
                        servicios = "En sus servicios cuenta con:\n" + "Habitaciones con vista a la Laguna de Nogales.\n" +
                                "Estacionamiento privado y seguridad 24 horas.",
                        contactos = "",
                        nombreContacto = "",
                        latitude = 18.821513,
                        longitude = -97.162616,
                    ),
                    Sitio(
                        nombre = "Posada \"Las Garzas\"",
                        categoria = "hoteles",
                        informacion = "Apegado al servicio de Airbnb, el Hotel Posada \"Las Garzas\" es un lugar acogedor para descansar y disfrutar del ambiente cálido de Nogales.",
                        historia = "Este hotel se fundó como un emprendimiento familiar para ofrecer un hospedaje cálido y con un toque hogareño. Ha ganado popularidad entre los turistas por su excelente servicio y atención personalizada. Su ubicación céntrica lo convierte en una excelente opción para viajeros que buscan comodidad y fácil acceso a los principales atractivos de la región.",
                        costos = "Precio de hospedaje: \$1000.00 la noche por habitación.",
                        horarios = "Reservaciones por Airbnb o WhatsApp.",
                        servicios = "En sus servicios cuenta con:\n" + "Wi-Fi y televisión por cable.\n" + "Area comun con jardin" +
                                "Alimentos en habitacion",
                        nombreContacto = "Contacto para reservaciones en Las Garzas:",
                        contactos = "272 246 0568",
                        latitude = 18.819446,
                        longitude = -97.164488,
                    ),
                    Sitio(
                        nombre = "Cabañas Chicahuaxtla \"Bosque Encantado\"",
                        categoria = "hoteles",
                        informacion = "Este sitio combina comodidad y la experiencia única de hospedarse en un bosque encantador.",
                        historia = "Las Cabañas Chicahuaxtla Bosque Encantado son una opción ideal para quienes buscan desconectarse del mundo y disfrutar de un alojamiento rústico rodeado de la naturaleza mágica de Nogales. Este sitio nació con la visión de brindar a los visitantes una experiencia auténtica de convivencia con la naturaleza. Desde su apertura, ha sido un punto de referencia para los amantes del ecoturismo, ofreciendo no solo alojamiento sino también actividades que conectan con el entorno.",
                        costos = "Precio de hospedaje:\n" + "\$900.00 la noche en Casa Agua (2 huespedes, 1 cama, 1 sofacama).\n" +
                                "\$870.00 la noche en Casa Tierra (2 huespedes, 1 cama).\n" +
                                "\$850.00 la noche en Casa Fuego (2 huespedes, 1 cama, 1 sofacama).\n" +
                                "\$840.00 la noche en Casa Luna (2 huespedes, 1 cama).\n" +
                                "\$840.00 la noche en Casa Sol (2 huespedes, 1 cama, 1 sofacama).",
                        horarios = "Check-in a partir de las 15:00 horas, Check-out antes de las 12:00 horas.",
                        servicios = "Habitaciones Airbnb, con todo incluido. Areas verdes para senderismo y fogata. Actividades de observacion de aves y talleres de conexion con la naturaleza. Internet satelital en todo el complejo.",
                        nombreContacto = "Reservaciones en Cabañas Chicahuaxtla:",
                        contactos = "55 8431 6070",
                        latitude = 18.854854,
                        longitude = -97.212309,

                        )
            )
            sitios.forEach { sitio ->
                db.sitioDao().insertSitio(sitio)
            }
        }

}