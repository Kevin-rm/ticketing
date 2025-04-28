<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/frontoffice/_layout">
    <layout:put block="content">
        <!-- Hero Section -->
        <div class="container py-5">
            <div class="row align-items-center">
                <div class="col-lg-6">
                    <h1 class="fw-bold text-primary display-4 mb-3">Voyagez en toute simplicité</h1>
                    <p class="lead mb-4">Réservez vos billets d'avion en quelques clics et profitez de nos meilleures offres pour vos prochains voyages.</p>
                    <div class="d-grid gap-2 d-md-flex">
                        <a href="<c:url value="/vols"/>" class="btn btn-primary btn-lg px-4">
                            <i class="bi bi-search me-2"></i>Rechercher un vol
                        </a>
                        <a href="<c:url value="/mes-reservations"/>" class="btn btn-outline-secondary btn-lg px-4">
                            <i class="bi bi-bookmark-check me-2"></i>Mes réservations
                        </a>
                    </div>
                </div>
                <div class="col-lg-6 mt-5 mt-lg-0 text-center">
                    <img src="<c:url value="/public/assets/images/hero-section.jpg"/>" alt="Un voyageur admirant un avion décollant" class="img-fluid rounded shadow-sm" width="80%">
                </div>
            </div>
        </div>

        <!-- Features Section -->
        <div class="bg-light py-5 mt-4">
            <div class="container">
                <h2 class="text-center fw-bold text-primary mb-5">Pourquoi choisir Ticketing ?</h2>
                <div class="row g-4">
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm border-0">
                            <div class="card-body text-center p-4">
                                <div class="rounded-circle bg-primary text-white d-inline-flex align-items-center justify-content-center mb-3" style="width: 60px; height: 60px;">
                                    <i class="bi bi-cash-coin fs-3"></i>
                                </div>
                                <h3 class="fs-4 fw-bold">Meilleurs prix</h3>
                                <p class="card-text">Bénéficiez des tarifs les plus compétitifs et des promotions exclusives sur tous nos vols.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm border-0">
                            <div class="card-body text-center p-4">
                                <div class="rounded-circle bg-primary text-white d-inline-flex align-items-center justify-content-center mb-3" style="width: 60px; height: 60px;">
                                    <i class="bi bi-shield-check fs-3"></i>
                                </div>
                                <h3 class="fs-4 fw-bold">Sécurité garantie</h3>
                                <p class="card-text">Vos paiements et vos informations personnelles sont sécurisés par les meilleurs systèmes de protection.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card h-100 shadow-sm border-0">
                            <div class="card-body text-center p-4">
                                <div class="rounded-circle bg-primary text-white d-inline-flex align-items-center justify-content-center mb-3" style="width: 60px; height: 60px;">
                                    <i class="bi bi-headset fs-3"></i>
                                </div>
                                <h3 class="fs-4 fw-bold">Support 24/7</h3>
                                <p class="card-text">Notre équipe de support client est disponible à tout moment pour répondre à vos questions.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- How It Works Section -->
        <div class="container py-5">
            <h2 class="text-center fw-bold text-primary mb-5">Comment ça marche ?</h2>
            <div class="row">
                <div class="col-lg-10 mx-auto">
                    <div class="row g-4">
                        <div class="col-md-4">
                            <div class="text-center">
                                <div class="bg-primary text-white rounded-circle d-inline-flex align-items-center justify-content-center mb-3" style="width: 50px; height: 50px;">
                                    <span class="fw-bold">1</span>
                                </div>
                                <h3 class="fs-5 fw-bold">Recherchez un vol</h3>
                                <p>Parcourez notre sélection de vols et choisissez celui qui correspond à vos besoins.</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="text-center">
                                <div class="bg-primary text-white rounded-circle d-inline-flex align-items-center justify-content-center mb-3" style="width: 50px; height: 50px;">
                                    <span class="fw-bold">2</span>
                                </div>
                                <h3 class="fs-5 fw-bold">Réservez vos places</h3>
                                <p>Sélectionnez le type de siège qui vous convient et complétez votre réservation.</p>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="text-center">
                                <div class="bg-primary text-white rounded-circle d-inline-flex align-items-center justify-content-center mb-3" style="width: 50px; height: 50px;">
                                    <span class="fw-bold">3</span>
                                </div>
                                <h3 class="fs-5 fw-bold">Recevez votre billet</h3>
                                <p>Téléchargez votre billet électronique et préparez-vous pour votre voyage.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- FAQ Section -->
        <div class="bg-light py-5">
            <div class="container">
                <h2 class="text-center fw-bold text-primary mb-5">Questions fréquentes</h2>
                <div class="row">
                    <div class="col-lg-8 mx-auto">
                        <div class="accordion" id="faqAccordion">
                            <div class="accordion-item">
                                <h2 class="accordion-header">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#faq1">
                                        Comment annuler ma réservation ?
                                    </button>
                                </h2>
                                <div id="faq1" class="accordion-collapse collapse show" data-bs-parent="#faqAccordion">
                                    <div class="accordion-body">
                                        Vous pouvez annuler votre réservation en vous rendant dans la section "Mes réservations" de votre compte. Cliquez sur le bouton "Annuler" à côté de la réservation concernée et confirmez l'annulation.
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq2">
                                        Puis-je modifier ma réservation après l'avoir confirmée ?
                                    </button>
                                </h2>
                                <div id="faq2" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                                    <div class="accordion-body">
                                        Une fois la réservation confirmée, il n'est pas possible de la modifier directement. Nous vous recommandons d'annuler votre réservation actuelle et d'en créer une nouvelle avec les détails mis à jour.
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq3">
                                        Comment obtenir mon billet électronique ?
                                    </button>
                                </h2>
                                <div id="faq3" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                                    <div class="accordion-body">
                                        Votre billet électronique est disponible dans la section "Mes réservations". Vous pouvez le télécharger au format PDF en cliquant sur le bouton "PDF" à côté de votre réservation.
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#faq4">
                                        Quels sont les types de sièges disponibles ?
                                    </button>
                                </h2>
                                <div id="faq4" class="accordion-collapse collapse" data-bs-parent="#faqAccordion">
                                    <div class="accordion-body">
                                        Nous proposons différents types de sièges selon les vols, notamment économique, premium et première classe. Les détails et les prix sont indiqués lors de la sélection du vol.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- CTA Section -->
        <div class="container py-5">
            <div class="row">
                <div class="col-lg-8 mx-auto text-center">
                    <h2 class="fw-bold text-primary mb-3">Prêt à voyager ?</h2>
                    <p class="lead mb-4">Découvrez notre sélection de vols et réservez dès maintenant pour bénéficier de nos meilleures offres.</p>
                    <a href="<c:url value="/vols"/>" class="btn btn-primary btn-lg px-4">
                        <i class="bi bi-airplane me-2"></i>Voir les vols disponibles
                    </a>
                </div>
            </div>
        </div>
    </layout:put>
</layout:extends>
