// On initialise la latitude et la longitude de Paris (centre de la carte)
	    var lat = 48.852969;
	    var lon = 2.349903;
	    var macarte = null;
            //var markerClusters; // Servira à stocker les groupes de marqueurs
            // Nous initialisons une liste de marqueurs
            var villes = {
                "Paris": { "lat": 48.852969, "lon": 2.349903 },
                "Brest": { "lat": 48.383, "lon": -4.500 },
                "Quimper": { "lat": 48.000, "lon": -4.100 },
                "Bayonne": { "lat": 43.500, "lon": -1.467 },
                "Marseille": {"lat":43.295222,"lon":5.386659}
            };
	    // Fonction d'initialisation de la carte
            function initMap() {
                // Nous définissons le dossier qui contiendra les marqueurs
                var iconBase = 'http://localhost/carte/icons/';
		// Créer l'objet "macarte" et l'insèrer dans l'élément HTML qui a l'ID "map"
                macarte = L.map('map').setView([lat, lon], 11);
               // markerClusters = L.markerClusterGroup(); // Nous initialisons les groupes de marqueurs
                // Leaflet ne récupère pas les cartes (tiles) sur un serveur par défaut. Nous devons lui préciser où nous souhaitons les récupérer. Ici, openstreetmap.fr
                L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
                    // Il est toujours bien de laisser le lien vers la source des données
                    attribution: 'données © <a href="//osm.org/copyright">OpenStreetMap</a>/ODbL - rendu <a href="//openstreetmap.fr">OSM France</a>',
                    minZoom: 1,
                    maxZoom: 20
                }).addTo(macarte);
                // Nous parcourons la liste des villes
                for (ville in villes) {
	            // Nous définissons l'icône à utiliser pour le marqueur, sa taille affichée (iconSize), sa position (iconAnchor) et le décalage de son ancrage (popupAnchor)
                    // var myIcon = L.icon({
                    //     iconUrl: iconBase + "autres.png",
                    //     iconSize: [50, 50],
                    //     iconAnchor: [25, 50],
                    //     popupAnchor: [-3, -76],
                    // });
                    var marker = L.marker([villes[ville].lat, villes[ville].lon]).addTo(macarte);
                    marker.bindPopup(ville);
                    //markerClusters.addLayer(marker); // Nous ajoutons le marqueur aux groupes
                }
               // macarte.addLayer(markerClusters);
            }
	    window.onload = function(){
		// Fonction d'initialisation qui s'exécute lorsque le DOM est chargé
		initMap(); 
	    };