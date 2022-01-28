package b.proxy.service.impl.webclimber

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNull
import org.jsoup.Jsoup
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset


class WebClimberDataExtractorTest {

    private val dataExtractor = WebClimberDataExtractor()


    @Test
    fun `data is parse correctly and please forgive me for this`() {
        val slot = dataExtractor.getSlot(Jsoup.parseBodyFragment(anExample).selectFirst("tr")!!)!!

        assertThat(slot.id, `is`("bookingBtn_61f2aca0c185c"))
        assertThat(slot.freeSpots, `is`(13))
        assertThat(slot.start, `is`(OffsetDateTime.of(2022, 1, 28, 11, 0, 0, 0, ZoneOffset.UTC)))
        assertThat(slot.end, `is`(OffsetDateTime.of(2022, 1, 28, 13, 0, 0, 0, ZoneOffset.UTC)))
    }

    @Test
    fun `data is parse correctly and please forgive me for this again`() {
        val slot = dataExtractor.getSlot(Jsoup.parseBodyFragment(anotherExample).selectFirst("tr")!!)!!

        assertThat(slot.id, `is`("bookingBtn_61f2aca0c185c"))
        assertThat(slot.freeSpots, `is`(9))
        assertThat(slot.start, `is`(OffsetDateTime.of(2022, 1, 8, 8, 33, 0, 0, ZoneOffset.UTC)))
        assertThat(slot.end, `is`(OffsetDateTime.of(2022, 1, 8, 20, 12, 0, 0, ZoneOffset.UTC)))
    }

    @Test
    fun `when the table has less than 3 td null is returned`() {
        val slot = dataExtractor.getSlot(Jsoup.parseBodyFragment(anotherExample2).selectFirst("tr")!!)
        assertThat(slot, IsNull())
    }

    @Test
    fun `token data is extracted as expected`() {
        val token = dataExtractor.getToken(Jsoup.parse(tokenExample))
        assertThat(token.token, `is`("5865ab007983e4c4e5fc82dca5cc6ba16f8d94c7"))
        assertThat(token.session, `is`("c70338b03849f218c836ba74ebd500d4"))
    }

    @Test
    fun `new booking url is extracted as expected`() {
        val newUrl = dataExtractor.getNewBookingUrl(Jsoup.parse(tokenExample))
        assertThat(
            newUrl,
            `is`("/de/booking/checkoutPurchase/3XGFXOaSO3F9S?alias=reservierung-zeitslot&webclimber_session=c70338b03849f218c836ba74ebd500d4")
        )
    }

    private val anExample = """
        <table class="table table-striped"><tbody>
    <tr> 
 <td>11:00 - 13:00 Uhr</td> 
 <td>13 freie Pl\xc3\xa4tze</td> 
 <td> <button class="bookingBtn btn btn-success btn-small" data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-28&amp;time=11%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1" id="bookingBtn_61f2aca0c185c" name="yt8" type="button"> Buchen </button> </td> 
</tr></tbody>
</table>"""

    private val anotherExample = """
        <table class="table table-striped"><tbody>
    <tr> 
 <td>08:33 - 20:12 Uhr</td> 
 <td>9 freie Pl\xc3\xa4tze</td> 
 <td> <button class="bookingBtn btn btn-success btn-small" data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-08&amp;time=11%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1" id="bookingBtn_61f2aca0c185c" name="yt8" type="button"> Buchen </button> </td> 
</tr></tbody>
</table>"""
    private val anotherExample2 = """
        <table class="table table-striped"><tbody>
    <tr> 
 <td>08:33 - 20:12 Uhr</td> 
 <td> <button class="bookingBtn btn btn-success btn-small" data-url="https://168.webclimber.de/de/booking/book/bouldern-urban-sports-club?date=2022-01-08&amp;time=11%3A00&amp;period=2&amp;places=1&amp;persons=1&amp;place_id=1" id="bookingBtn_61f2aca0c185c" name="yt8" type="button"> Buchen </button> </td> 
</tr></tbody>
</table>"""


    private val tokenExample =
        """b'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"\n        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">\n
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">\n
<head>\n
    <base href="/"/>
    \n
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    \n
    <meta name="language" content="en"/>
    \n\n
    <meta content="initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
          name="viewport">
    \n
    <meta content="yes" name="apple-mobile-web-app-capable">
    \n\n
    <script src="js/jquery-1.7.2.min.js"></script>
    \n\n
    <script src="js/bootstrap.min.js"></script>
    \n
    <script src="js/bootstrap.bootbox.min.js"></script>
    \n
    <script src="js/jquery.toggle.buttons.js"></script>
    \n
    <script src="js/iframeResizer.contentWindow.min.js"></script>
    \n\n
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
    \n
    <link rel="stylesheet" type="text/css"
          href="/css/bootstrap-responsive.min.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/unicorn.main.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/unicorn.light-blue.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/font.awesome.min.css"/>
    \n
    <link rel="stylesheet" type="text/css"
          href="/css/font.awesome.icon.min.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/cus-icons.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/animate.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/pnotify.custom.min.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/main.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/main.table.css"/>
    \n
    <link rel="stylesheet" type="text/css" href="/css/mainPublic.css"/>
    \n
    <link rel="stylesheet" type="text/css"
          href="/assets/b21662d1/css/main.public.booking.css"/>
    \n
    <script type="text/javascript"
            src="/js/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    \n
    <script type="text/javascript"
            src="/js/jquery-ui-1.11.4.custom.min.js"></script>
    \n
    <script type="text/javascript" src="/js/jquery.md5.js"></script>
    \n
    <script type="text/javascript" src="/js/yii/jquery.ba.bbq.min.js"></script>
    \n
    <script type="text/javascript"
            src="/js/yii/jquery-yiiactiveform.js"></script>
    \n
    <script type="text/javascript" src="/js/yii/jquery-yiigridview.js"></script>
    \n
    <script type="text/javascript" src="/js/unicorn.js"></script>
    \n
    <script type="text/javascript" src="/js/unicorn.tables.js"></script>
    \n
    <script type="text/javascript"
            src="/js/jquery.noty.packaged.min.js"></script>
    \n
    <script type="text/javascript" src="/js/noty.theme.bootstrap.js"></script>
    \n
    <script type="text/javascript" src="/js/pnotify.custom.min.js"></script>
    \n
    <script type="text/javascript" src="/js/mainPublic.js"></script>
    \n<title>Termin &quot;Reservierung Zeitslot - So, 06.02.2022 09:00 - 11:00&quot;
        buchen - ostbloc GmbH</title>\n\n
    <meta name="description" content="Freeclimber">
    \n
    <meta name="author" content="APS Services GmbH"/>
    \n
    <meta name="copyright" content="APS Services GmbH"/>
    \n
    <meta name="publisher" content="APS Services GmbH"/>
    \n\n\n\t
    <style type="text/css">#breadcrumb {
        \r\ndisplay: none;
        \r\n
    }</style>
</head>
\n
<body>\n
<noscript>\n
    <div id="noscript" class="alert alert-info">\n\t\tBitte aktivieren Sie
        Javascript im Browser.
    </div>
    \n
    <div class="modal-backdrop"></div>
    \n
</noscript>
\n\n\n
<div id="coursewrapper">\n\n
    <div id="header">\n <h1>\n\t\t\t<img src="/img/pdf/logo_153.png"/><span>ostbloc GmbH</span>
    </h1>\n
    </div>
    \n\n
    <div id="breadcrumb">\n\t\t\t\t\t
        <div class="breadcrumbs">\n<a
                href="/de/booking?webclimber_session=c70338b03849f218c836ba74ebd500d4">Angebote</a><a
                href="/de/booking/offer/reservierung-zeitslot?webclimber_session=c70338b03849f218c836ba74ebd500d4">Reservierung
            Zeitslot</a><a
                href="/de/booking/checkoutPurchase/3XGFXOaSO3F9S?alias=reservierung-zeitslot&webclimber_session=c70338b03849f218c836ba74ebd500d4">Termin
            &quot;So, 06.02.2022, 09:00 - 11:00&quot; buchen</a></div>
        \t\t
    </div>
    \n\n
    <div id="main" class="container-fluid">\n
        <div id="flash" class="row-fluid">\n
            <div class="span12">\n\t\t\t\t\t\t\t\t\n</div>
            \n
        </div>
        \n
        <div id="maincontent">\n\t\t\t\n\n
            <div class="span12" id="checkoutPurchase">\n
                <div class="widget-box">\n
                    <div class="widget-title">\n\t\t\t<span class="icon">\n\t\t\t\t<i
                            class="fa fa-fw fa-shopping-cart"></i>\n\t\t\t</span>\n
                        <h5>Termin buchen</h5>\n
                    </div>
                    \n\n
                    <div class="widget-content nopadding">\n\n\t\t\t\t
                        <div style="text-align:center"><h3>Reservierung Zeitslot<br>So,
                            06.02.2022, 09:00 - 11:00</h3></div>
                        <div id="countdownWrapper">Pl\xc3\xa4tze sind noch
                            f\xc3\xbcr <span id="countdownContainer"></span>
                            Minuten reserviert
                        </div>
                        <div class="bookingWizard">\n
                            <div class="bookingWizard-steps">\n
                                <div class="bookingWizard-progress">\n
                                    <div class="bookingWizard-progress-line"
                                         style="width: 10%;"></div>
                                    \n
                                </div>
                                \n
                                <div class="bookingWizard-step active">\n <p>
                                    Buchen</p>\n
                                    <div class="bookingWizard-step-icon"></div>
                                    \n
                                </div>
                                \n
                                <div class="bookingWizard-step">\n <p>
                                    Zahlungsart</p>\n
                                    <div class="bookingWizard-step-icon"></div>
                                    \n
                                </div>
                                \n
                                <div class="bookingWizard-step">\n <p>
                                    Zusammenfassung</p>\n
                                    <div class="bookingWizard-step-icon"></div>
                                    \n
                                </div>
                                \n
                                <div class="bookingWizard-step">\n <p>
                                    Bezahlen</p>\n
                                    <div class="bookingWizard-step-icon"></div>
                                    \n
                                </div>
                                \n
                                <div class="bookingWizard-step">\n <p>Fertig</p>
                                    \n
                                    <div class="bookingWizard-step-icon"></div>
                                    \n
                                </div>
                                \n
                            </div>
                            \n
                        </div>
                        \n\t\t\t\t
                        <form class="labelFix form-horizontal"
                              id="booking-order-form"
                              action="/de/booking/checkoutPurchase/3XGFXOaSO3F9S?alias=reservierung-zeitslot"
                              method="post"><input type="hidden"
                                                   name="webclimber_session"
                                                   value="c70338b03849f218c836ba74ebd500d4"/>\n<input
                                type="hidden"
                                value="5865ab007983e4c4e5fc82dca5cc6ba16f8d94c7"
                                name="YII_CSRF_TOKEN"/>\n\t\t\t\t
                            <div class="alert alert-block alert-error"
                                 id="booking-order-form_es_"
                                 style="display:none"><p>Bitte beheben Sie
                                folgende Eingabefehler:</p>\n
                                <ul>
                                    <li>dummy</li>
                                </ul>
                            </div>
                            <div class="container-fluid">\n
                                <div class="row-fluid">\n
                                    <div class="span6">\n
                                        <div class="widget-box">\n
                                            <div class="widget-title">\n <h5>
                                                Teilnehmer Daten</h5>\n
                                            </div>
                                            \n
                                            <div class="widget-content nopadding">
                                                \n\t\t\t\t\t\t\t\t\t
                                                <div class="control-group">
                                                    <label class="control-label"
                                                           for="BookingOrder_0_anrede">Anrede</label>
                                                    <div class="controls"><input
                                                            id="ytBookingOrder_0_anrede"
                                                            type="hidden"
                                                            value=""
                                                            name="BookingOrder[0][anrede]"/>
                                                        <div id="BookingOrder_0_anrede">
                                                            <label class="radio radio-inline"><input
                                                                    placeholder="[0]anrede"
                                                                    id="BookingOrder_0_anrede_0"
                                                                    value="1"
                                                                    type="radio"
                                                                    name="BookingOrder[0][anrede]"/><span>Herr</span></label>\n<label
                                                                class="radio radio-inline"><input
                                                                placeholder="[0]anrede"
                                                                id="BookingOrder_0_anrede_1"
                                                                value="2"
                                                                type="radio"
                                                                name="BookingOrder[0][anrede]"/><span>Frau</span></label>
                                                        </div>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_anrede_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_vorname">Vorname
                                                        <span class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="Vorname"
                                                            name="BookingOrder[0][vorname]"
                                                            id="BookingOrder_0_vorname"
                                                            type="text"
                                                            value=""/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_vorname_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_nachname">Nachname
                                                        <span class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="Nachname"
                                                            name="BookingOrder[0][nachname]"
                                                            id="BookingOrder_0_nachname"
                                                            type="text"
                                                            value=""/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_nachname_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_strasse">Strasse
                                                        <span class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="Strasse"
                                                            name="BookingOrder[0][strasse]"
                                                            id="BookingOrder_0_strasse"
                                                            type="text"
                                                            value=""/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_strasse_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_plz">Plz
                                                        <span class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="Plz"
                                                            name="BookingOrder[0][plz]"
                                                            id="BookingOrder_0_plz"
                                                            type="text"
                                                            value=""/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_plz_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_ort">Ort
                                                        <span class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="Ort"
                                                            name="BookingOrder[0][ort]"
                                                            id="BookingOrder_0_ort"
                                                            type="text"
                                                            value=""/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_ort_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_email">Email
                                                        <span class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="Email"
                                                            name="BookingOrder[0][email]"
                                                            id="BookingOrder_0_email"
                                                            type="text"
                                                            value=""/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_email_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_email_check">E-Mail
                                                        wiederholen <span
                                                                class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="E-Mail wiederholen"
                                                            name="BookingOrder[0][email_check]"
                                                            id="BookingOrder_0_email_check"
                                                            type="text"/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_email_check_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_telefon">Telefon
                                                        <span class="required">*</span></label>
                                                    <div class="controls"><input
                                                            class="input-large"
                                                            maxlength="255"
                                                            placeholder="Telefon"
                                                            name="BookingOrder[0][telefon]"
                                                            id="BookingOrder_0_telefon"
                                                            type="text"
                                                            value=""/>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_telefon_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div id="artikel_container">
                                                    <div class="control-group">
                                                        <label class="control-label required"
                                                               for="BookingOrder_0_artikel_id">Artikel
                                                            <span class="required">*</span></label>
                                                        <div class="controls">
                                                            <select class="input-xlarge articleSelect"
                                                                    placeholder="[0]artikel Id"
                                                                    name="BookingOrder[0][artikel_id]"
                                                                    id="BookingOrder_0_artikel_id">\n
                                                                <option value="48"
                                                                        selected="selected">
                                                                    Reservierung
                                                                    Zwei Stunden
                                                                </option>
                                                                \n</select>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_0_artikel_id_em_"
                                                                 style="display:none"></div>
                                                            <div class="help-block"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="control-group">
                                                    <label class="control-label required"
                                                           for="BookingOrder_0_teilnehmer">Teilnehmer
                                                        <span class="required">*</span></label>
                                                    <div class="controls">
                                                        <select class="input-mini"
                                                                placeholder="[0]teilnehmer"
                                                                name="BookingOrder[0][teilnehmer]"
                                                                id="BookingOrder_0_teilnehmer">\n
                                                            <option value="1"
                                                                    selected="selected">
                                                                1
                                                            </option>
                                                            \n
                                                            <option value="2">
                                                                2
                                                            </option>
                                                            \n
                                                            <option value="3">
                                                                3
                                                            </option>
                                                            \n
                                                            <option value="4">
                                                                4
                                                            </option>
                                                            \n
                                                            <option value="5">
                                                                5
                                                            </option>
                                                            \n
                                                            <option value="6">
                                                                6
                                                            </option>
                                                            \n
                                                            <option value="7">
                                                                7
                                                            </option>
                                                            \n
                                                            <option value="8">
                                                                8
                                                            </option>
                                                            \n
                                                            <option value="9">
                                                                9
                                                            </option>
                                                            \n
                                                            <option value="10">
                                                                10
                                                            </option>
                                                            \n</select>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_teilnehmer_em_"
                                                             style="display:none"></div>
                                                        <div class="help-block"></div>
                                                    </div>
                                                </div>
                                                <div class="form-actions">\n
                                                </div>
                                                \n
                                            </div>
                                            \n
                                        </div>
                                        \n\n\n\t\t\t\t\t\t\t\n\n\t\t\t\t\t\t\t\t\n
                                        <div class="widget-box"
                                             id="extraMember">\n
                                            <div class="widget-title">\n <h5>
                                                Weitere Teilnehmer</h5>\n
                                            </div>
                                            \n
                                            <div class="widget-content nopadding">
                                                \n\t\t\t\t\t\t\t\t\t\t
                                                <div id="extraMember_1"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>1. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_1_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[1][vorname]"
                                                                   id="BookingOrder_1_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_1_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_1_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[1][nachname]"
                                                                   id="BookingOrder_1_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_1_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_2"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>2. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_2_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[2][vorname]"
                                                                   id="BookingOrder_2_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_2_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_2_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[2][nachname]"
                                                                   id="BookingOrder_2_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_2_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_3"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>3. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_3_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[3][vorname]"
                                                                   id="BookingOrder_3_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_3_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_3_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[3][nachname]"
                                                                   id="BookingOrder_3_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_3_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_4"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>4. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_4_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[4][vorname]"
                                                                   id="BookingOrder_4_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_4_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_4_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[4][nachname]"
                                                                   id="BookingOrder_4_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_4_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_5"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>5. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_5_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[5][vorname]"
                                                                   id="BookingOrder_5_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_5_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_5_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[5][nachname]"
                                                                   id="BookingOrder_5_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_5_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_6"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>6. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_6_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[6][vorname]"
                                                                   id="BookingOrder_6_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_6_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_6_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[6][nachname]"
                                                                   id="BookingOrder_6_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_6_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_7"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>7. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_7_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[7][vorname]"
                                                                   id="BookingOrder_7_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_7_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_7_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[7][nachname]"
                                                                   id="BookingOrder_7_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_7_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_8"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>8. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_8_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[8][vorname]"
                                                                   id="BookingOrder_8_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_8_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_8_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[8][nachname]"
                                                                   id="BookingOrder_8_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_8_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_9"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>9. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_9_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[9][vorname]"
                                                                   id="BookingOrder_9_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_9_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_9_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[9][nachname]"
                                                                   id="BookingOrder_9_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_9_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div id="extraMember_10"
                                                     class="row-fluid">
                                                    <div class="control-group">
                                                        <legend>10. Weiterer
                                                            Teilnehmer
                                                        </legend>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_10_vorname">Vorname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Vorname"
                                                                   name="BookingOrder[10][vorname]"
                                                                   id="BookingOrder_10_vorname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_10_vorname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                    <div class="control-group">
                                                        <label class="control-label"
                                                               for="BookingOrder_10_nachname">Nachname</label>
                                                        <div class="controls">
                                                            <input class="input-large"
                                                                   maxlength="255"
                                                                   placeholder="Nachname"
                                                                   name="BookingOrder[10][nachname]"
                                                                   id="BookingOrder_10_nachname"
                                                                   type="text"/>
                                                            <div class="help-inline error"
                                                                 id="BookingOrder_10_nachname_em_"
                                                                 style="display:none"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                                \n
                                            </div>
                                            \n
                                        </div>
                                        \n\n\t\t\t\t\t\t\t\t\n\n
                                    </div>
                                    \n
                                    <div class="span6">\n\n
                                        <div class="widget-box">\n
                                            <div class="widget-title">\n <h5>
                                                Warenkorb</h5>\n
                                            </div>
                                            \n
                                            <div class="widget-content nopadding">
                                                \n
                                                <table class="table table-striped cartTable">
                                                    \n
                                                    <tbody>
                                                    \n\t\t\t\t\t\t\t\t\t\t
                                                    <tr>
                                                        <td style="text-align:center">
                                                            Menge
                                                        </td>
                                                        <td></td>
                                                        <td style="text-align:right">
                                                            Ez. Preis
                                                        </td>
                                                        <td style="text-align:right">
                                                            Summe
                                                        </td>
                                                    </tr>
                                                    <tr class="mainArticle"
                                                        data-id="48"
                                                        data-price="0.00"
                                                        data-qty="0"
                                                        data-sum="0">
                                                        \n\t\t\t\t\t\t\t\t\t\t\t\t
                                                        <td style="text-align:center"
                                                            id="mainArticleQty48"
                                                            class="mainArticleQty">
                                                            0
                                                        </td>
                                                        \n\t\t\t\t\t\t\t\t\t\t\t\t
                                                        <td>Reservierung Zwei
                                                            Stunden
                                                        </td>
                                                        \n\t\t\t\t\t\t\t\t\t\t\t\t
                                                        <td style="text-align:right"
                                                            id="mainArticlePrice48"
                                                            class="mainArticlePrice">
                                                            0.00\xc2\xa0\xe2\x82\xac
                                                        </td>
                                                        \n\t\t\t\t\t\t\t\t\t\t\t\t
                                                        <td style="text-align:right"
                                                            id="mainArticleSum48"
                                                            class="mainArticleSum">
                                                            0.00\xc2\xa0\xe2\x82\xac
                                                        </td>
                                                        \n\t\t\t\t\t\t\t\t\t\t\t\t
                                                    </tr>
                                                    <tr>
                                                        <td colspan="3"
                                                            style="text-align:right;font-weight:bold;">
                                                            Gesamtsumme:
                                                        </td>
                                                        <td style="text-align:right;font-weight:bold;"
                                                            id="cartSum"></td>
                                                    </tr>
                                                    </tbody>
                                                    \n
                                                </table>
                                                \n
                                                <div class="form-actions"
                                                     id="cart_footer">
                                                    \n\t\t\t\t\t\t\t\t\t\t
                                                    <tr>
                                                        <td colspan="2"
                                                            id="cart_footer_text">
                                                            Alle Preise
                                                            inklusive
                                                            Mehrwertsteuer
                                                        </td>
                                                    </tr>
                                                </div>
                                                \n
                                            </div>
                                            \n
                                        </div>
                                        \n\n\n
                                    </div>
                                    \n
                                </div>
                                \n\n\t\t\t\t\t
                                <div class="row-fluid">\n
                                    <div class="span12">\n
                                        <div class="widget-box">\n
                                            <div class="widget-title">\n <h5>
                                                Bemerkung</h5>\n
                                            </div>
                                            \n
                                            <div class="widget-content nopadding bookingMessage">
                                                \n\n\t\t\t\t\t\t\t\t\t\t
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <textarea rows="6"
                                                                  class="input-large"
                                                                  style="width:95%;"
                                                                  placeholder="Bemerkung"
                                                                  name="BookingOrder[0][bemerkung]"
                                                                  id="BookingOrder_0_bemerkung"></textarea>
                                                        <div class="help-inline error"
                                                             id="BookingOrder_0_bemerkung_em_"
                                                             style="display:none"></div>
                                                    </div>
                                                </div>
                                                <div class="form-actions">\n
                                                </div>
                                                \n
                                            </div>
                                            \n
                                        </div>
                                        \n
                                    </div>
                                    \n
                                </div>
                                \n\t\t\t\t\t\t\n
                            </div>
                            \n\n
                            <div class="form-actions">\n\t\t\t\t\t<a class="btn"
                                                                     id="yw0"
                                                                     href="/de/booking/cancel/3XGFXOaSO3F9S?alias=reservierung-zeitslot&webclimber_session=c70338b03849f218c836ba74ebd500d4">Zur\xc3\xbcck</a>
                                <button class="btn btn-success" id="yw1"
                                        type="submit" name="yt0">Weiter zur
                                    Zahlungsart
                                </button>
                            </div>
                            \n
                    </div>
                    \n
                </div>
                \n
            </div>
            \n\n\n</form>        </div>
        \n
    </div>
    \n\n
    <div id="footer" class="row-fluid">\n
        <div class="span12">\n
            <footer>\n\t\t\t\t<span id="imprintLink"><a
                    href="/de/booking/imprint?webclimber_session=c70338b03849f218c836ba74ebd500d4"
                    id="terms">Impressum</a></span><span
                    class="footerSeperator"> | </span><span id="termsLink"><a
                    href="/de/booking/terms?webclimber_session=c70338b03849f218c836ba74ebd500d4"
                    id="terms">AGB</a></span><span
                    class="footerSeperator"> | </span><span id="privacyLink"><a
                    href="/de/booking/privacy?webclimber_session=c70338b03849f218c836ba74ebd500d4"
                    id="privacy">Datenschutz-Erkl\xc3\xa4rung</a></span><span
                    class="footerSeperator"> | </span><span id="disclaimerLink"><a
                    href="/de/booking/disclaimer?webclimber_session=c70338b03849f218c836ba74ebd500d4"
                    id="disclaimer">Widerrufsbelehrung</a></span><span
                    class="footerSeperator"> | </span><span id="ruleLink"><a
                    href="/de/booking/rule?webclimber_session=c70338b03849f218c836ba74ebd500d4"
                    id="terms">Hallen-Regeln</a></span><span
                    class="footerSeperator"> | </span><span
                    id="agreementLink"><a
                    href="/de/booking/agreement?webclimber_session=c70338b03849f218c836ba74ebd500d4"
                    id="terms">Einverst\xc3\xa4ndniserkl\xc3\xa4rung</a></span>
                <br/><br/>\n
            </footer>
            \n
        </div>
        \n
    </div>
    \n
</div>
\n\n
<div id="loadingContainer">\n
    <div id="loadingShadow"></div>
    \n
    <div id="loadingContent">\n
        <div id="loadingImage"></div>
        \n
        <div id="loadingText"></div>
        \n
    </div>
    \n
</div>
\n\n
<script type="text/javascript"
        src="/js/jquery.plugin.min.js?v=20170311"></script>
\n
<script type="text/javascript"
        src="/js/jquery.countdown.min.js?v=20170311"></script>
\n
<script type="text/javascript">\n/*<![CDATA[*/\njQuery(function (${'$'}) {\n\n
    ${'$'}(\'#countdownContainer\').countdown({\n                                        until: +3600, \n                                        format: \'MS\', \n                                        compact: true,\n                                        tickInterval: 1,\n                                        expiryUrl: \'/de/booking/cancel/3XGFXOaSO3F9S?alias=reservierung-zeitslot\',\n                                        onExpiry: function(){\n                                            \n                                        }\n                                        }); \n                                    \n\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t    App.noParticipate = false;\n\t\t\n\t\t\t\t\t\t\t\t\t\n\n\t\t\t\t\t\t\t\t\t                        \n\t\t\t\t\t\t\t\t\t            App.bookingTotalSum = 0;\n\t\t\t\t\t\t\t\t\t            \n\t\t\t\t\t\t\t\t\t            App.hasExtraMember = 1;\n\t\t\t\t\t\t\t\t\t            App.perPersonPrice = 0;\n\t\t\t\t\t\t\t\t\t                                                                        \n                                                App.calculateTotalSum = function(){    \n                                                    App.bookingTotalSum = 0;\n                                                                                                         \n                                                    var article_qty = [];\n                                                    for (var i = 0; i < ${'$'}(\'.mainArticle\').length; i++) {\n                                                       var articleEl = ${'$'}(${'$'}(\'.mainArticle\')[i]);\n                                                       var artikel_id = articleEl.data(\'id\');                                                    \n                                                       articleEl.data(\'qty\', 0);\n                                                       article_qty[artikel_id] = 0;\n                                                    }                                                    \n                                                    \n                                                    if(App.hasExtraMember){\n                                                        if(App.noParticipate){\n                                                            for (var i = 1; i <= ${'$'}(\'#BookingOrder_0_teilnehmer\').val(); i++) {\n                                                                var selected_id = ${'$'}(${'$'}(\'.articleSelect\')[i]).val();\n                                                                article_qty[selected_id]++;                                                       \n                                                            }\n                                                        }else{\n                                                            for (var i = 0; i < ${'$'}(\'#BookingOrder_0_teilnehmer\').val(); i++) {\n                                                                var selected_id = ${'$'}(${'$'}(\'.articleSelect\')[i]).val();\n                                                                article_qty[selected_id]++;                                                       \n                                                            } \n                                                        }    \n                                                    }else{\n                                                        if(App.perPersonPrice){\n                                                            article_qty[${'$'}(${'$'}(\'.articleSelect\')[0]).val()] = ${'$'}(\'#BookingOrder_0_teilnehmer\').val()\n                                                        }else{\n                                                            article_qty[${'$'}(${'$'}(\'.articleSelect\')[0]).val()] = 1\n                                                        }\n                                                    }\n                                                     \n                                                    for (var i = 0; i < ${'$'}(\'.mainArticle\').length; i++) {\n                                                    \n                                                       var articleEl = ${'$'}(${'$'}(\'.mainArticle\')[i]);\n                                                       var artikel_id = articleEl.data(\'id\');\n                                                       \n                                                       articleEl.data(\'qty\', article_qty[artikel_id])\n                                                       var qty = articleEl.data(\'qty\');\n                  \n                                                       var price = articleEl.data(\'price\');\n                                                       var qty1 = articleEl.data(\'qty1\');\n                                                       var price1 = articleEl.data(\'price1\');\n                                                       var qty2 = articleEl.data(\'qty2\');\n                                                       var price2 = articleEl.data(\'price2\');\n                                                       var qty3 = articleEl.data(\'qty3\');\n                                                       var price3 = articleEl.data(\'price3\');\n                                                       \n                                                       if(qty >= qty3){\n                                                            price = price3;\n                                                       }else if(qty >= qty2){\n                                                            price = price2;\n                                                       }else if(qty >= qty1){\n                                                            price = price1;\n                                                       } \n                                                                                                             \n                                                       ${'$'}(\'#mainArticleQty\'+artikel_id).html(qty);     \n                                                       ${'$'}(\'#mainArticlePrice\'+artikel_id).html(price.toFixed(2) + \' \xe2\x82\xac\');   \n                                                       \n                                                       var sum = (qty * price).toFixed(2);\n                              \n                                                       articleEl.data(\'sum\', sum);\n                                                       \n                                                       ${'$'}(\'#mainArticleSum\'+artikel_id).html(sum + \' \xe2\x82\xac\');\n                                                \n                                                       App.bookingTotalSum += parseFloat(sum);\n                                                    }\n                                                    \n                                                    if(${'$'}(\'.upgradeArticleSum\').length){\n                                                        for (var i = 0; i < ${'$'}(\'.upgradeArticleSum\').length; i++) {\n                                                        \n                                                           var articleEl = ${'$'}(${'$'}(\'.upgradeArticle\')[i]);\n                                                           var artikel_id = articleEl.data(\'id\');\n                                                           var qty = ${'$'}(\'#BookingOrder_0_articleUpgradesQty_\' + artikel_id).val();\n                      \n                                                           var price = articleEl.data(\'price\');\n                                                           var qty1 = articleEl.data(\'qty1\');\n                                                           var price1 = articleEl.data(\'price1\');\n                                                           var qty2 = articleEl.data(\'qty2\');\n                                                           var price2 = articleEl.data(\'price2\');\n                                                           var qty3 = articleEl.data(\'qty3\');\n                                                           var price3 = articleEl.data(\'price3\');\n                                                           \n                                                           if(qty >= qty3){\n                                                                price = price3;\n                                                           }else if(qty >= qty2){\n                                                                price = price2;\n                                                           }else if(qty >= qty1){\n                                                                price = price1;\n                                                           } \n                                                                                                                 \n                                                           //${'$'}(\'#upgradeArticleQty\'+artikel_id).html(qty);     \n                                                           ${'$'}(\'#upgradeArticlePrice\'+artikel_id).html(price.toFixed(2) + \' \xe2\x82\xac\');   \n                                                           \n                                                           var sum = (qty * price).toFixed(2);\n                                  \n                                                           articleEl.data(\'sum\', sum);\n                                                           \n                                                           ${'$'}(\'#upgradeArticleSum\'+artikel_id).html(sum + \' \xe2\x82\xac\');\n                                                    \n                                                           App.bookingTotalSum += parseFloat(sum);                                                        \n                                                        \n                                                        \n                                                        }\n                                                    }\n                                                    \n                                                    if(App.bookingTotalSum >= 0){\n                                                        ${'$'}(\'#cartSum\').html(App.bookingTotalSum.toFixed(2) + \' \xe2\x82\xac\')\n                                                    }\n                                                    \n                                                }\n                                                App.calculateTotalSum();        \n                                            \n                                            \n\t\t\t\t\t\t\t\t\t\n                                                ${'$'}(\'#BookingOrder_0_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                                });\t\t\t\t\t\t\t\t\t\n                                                ${'$'}(\'#BookingOrder_0_teilnehmer\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                                });\n\t\t\t\t\t\t\t\t\t\t\t\n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_0_teilnehmer\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n\t\t\t\t\t\t\t\t\t          //App.calculateTotalSum();\n                                            \n\n                                    ${'$'}(\'#BookingOrder_0_teilnehmer\').on(\'change\', function(e) {\n                                        App.changeMember();\n                                    });\n                                    \n                                    App.changeMember = function (){\n                                    \n                                        var anz = ${'$'}(\'#BookingOrder_0_teilnehmer\').val();\n                                        if(App.noParticipate){\n                                            anz++;\n                                        }\n                                                                                \n                                        for(var i=1;i<=20;i++){\n                                            ${'$'}(\'#extraMember_\' + i).hide();\n                                        }\n                                        \n                                        if(anz > 1){\n                                            ${'$'}(\'#extraMember\').show();\n                                            for(var i=1;i<anz;i++){                                        \n                                                ${'$'}(\'#extraMember_\' + i).show();\n                                            }\n                                        }else{\n                                            ${'$'}(\'#extraMember\').hide();\n                                        }\n                                        \n                                    }\n                                    \n                                    App.changeMember();\n                                    \n                                   \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_1_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_2_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_3_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_4_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_5_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_6_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_7_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_8_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_9_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \n\n\t\t\t\t\t\t\t\t\t          ${'$'}(\'#BookingOrder_10_artikel_id\').on(\'change\', function(e) {\n                                                    App.calculateTotalSum();\n                                              });\n                                            \njQuery(\'#booking-order-form\').yiiactiveform({\'afterValidate\':App.afterFormValidationAjax,\'validateOnSubmit\':true,\'validateOnChange\':false,\'validateOnType\':false,\'errorCallback\':function() {\n                           location.href = "/de/booking/checkoutPurchase/3XGFXOaSO3F9S?alias=reservierung-zeitslot&reload=1";\n                        },\'inputContainer\':\'div.control\\x2Dgroup\',\'attributes\':[{\'id\':\'BookingOrder_0_anrede\',\'inputID\':\'BookingOrder_0_anrede\',\'errorID\':\'BookingOrder_0_anrede_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Danrede\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_vorname\',\'inputID\':\'BookingOrder_0_vorname\',\'errorID\':\'BookingOrder_0_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dvorname\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_nachname\',\'inputID\':\'BookingOrder_0_nachname\',\'errorID\':\'BookingOrder_0_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dnachname\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_strasse\',\'inputID\':\'BookingOrder_0_strasse\',\'errorID\':\'BookingOrder_0_strasse_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dstrasse\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_plz\',\'inputID\':\'BookingOrder_0_plz\',\'errorID\':\'BookingOrder_0_plz_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dplz\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_ort\',\'inputID\':\'BookingOrder_0_ort\',\'errorID\':\'BookingOrder_0_ort_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dort\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_email\',\'inputID\':\'BookingOrder_0_email\',\'errorID\':\'BookingOrder_0_email_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Demail\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_email_check\',\'inputID\':\'BookingOrder_0_email_check\',\'errorID\':\'BookingOrder_0_email_check_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Demail_check\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_telefon\',\'inputID\':\'BookingOrder_0_telefon\',\'errorID\':\'BookingOrder_0_telefon_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dtelefon\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_artikel_id\',\'inputID\':\'BookingOrder_0_artikel_id\',\'errorID\':\'BookingOrder_0_artikel_id_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dartikel_id\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_0_teilnehmer\',\'inputID\':\'BookingOrder_0_teilnehmer\',\'errorID\':\'BookingOrder_0_teilnehmer_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dteilnehmer\',\'enableAjaxValidation\':true,\'status\':1},{\'id\':\'BookingOrder_1_vorname\',\'inputID\':\'BookingOrder_1_vorname\',\'errorID\':\'BookingOrder_1_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B1\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_1_nachname\',\'inputID\':\'BookingOrder_1_nachname\',\'errorID\':\'BookingOrder_1_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B1\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_2_vorname\',\'inputID\':\'BookingOrder_2_vorname\',\'errorID\':\'BookingOrder_2_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B2\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_2_nachname\',\'inputID\':\'BookingOrder_2_nachname\',\'errorID\':\'BookingOrder_2_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B2\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_3_vorname\',\'inputID\':\'BookingOrder_3_vorname\',\'errorID\':\'BookingOrder_3_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B3\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_3_nachname\',\'inputID\':\'BookingOrder_3_nachname\',\'errorID\':\'BookingOrder_3_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B3\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_4_vorname\',\'inputID\':\'BookingOrder_4_vorname\',\'errorID\':\'BookingOrder_4_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B4\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_4_nachname\',\'inputID\':\'BookingOrder_4_nachname\',\'errorID\':\'BookingOrder_4_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B4\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_5_vorname\',\'inputID\':\'BookingOrder_5_vorname\',\'errorID\':\'BookingOrder_5_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B5\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_5_nachname\',\'inputID\':\'BookingOrder_5_nachname\',\'errorID\':\'BookingOrder_5_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B5\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_6_vorname\',\'inputID\':\'BookingOrder_6_vorname\',\'errorID\':\'BookingOrder_6_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B6\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_6_nachname\',\'inputID\':\'BookingOrder_6_nachname\',\'errorID\':\'BookingOrder_6_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B6\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_7_vorname\',\'inputID\':\'BookingOrder_7_vorname\',\'errorID\':\'BookingOrder_7_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B7\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_7_nachname\',\'inputID\':\'BookingOrder_7_nachname\',\'errorID\':\'BookingOrder_7_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B7\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_8_vorname\',\'inputID\':\'BookingOrder_8_vorname\',\'errorID\':\'BookingOrder_8_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B8\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_8_nachname\',\'inputID\':\'BookingOrder_8_nachname\',\'errorID\':\'BookingOrder_8_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B8\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_9_vorname\',\'inputID\':\'BookingOrder_9_vorname\',\'errorID\':\'BookingOrder_9_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B9\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_9_nachname\',\'inputID\':\'BookingOrder_9_nachname\',\'errorID\':\'BookingOrder_9_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B9\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_10_vorname\',\'inputID\':\'BookingOrder_10_vorname\',\'errorID\':\'BookingOrder_10_vorname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B10\\x5Dvorname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_10_nachname\',\'inputID\':\'BookingOrder_10_nachname\',\'errorID\':\'BookingOrder_10_nachname_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B10\\x5Dnachname\',\'enableAjaxValidation\':true},{\'id\':\'BookingOrder_0_bemerkung\',\'inputID\':\'BookingOrder_0_bemerkung\',\'errorID\':\'BookingOrder_0_bemerkung_em_\',\'model\':\'BookingOrder\',\'name\':\'\\x5B0\\x5Dbemerkung\',\'enableAjaxValidation\':true,\'status\':1},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true},{\'summary\':true}],\'summaryID\':\'booking\\x2Dorder\\x2Dform_es_\',\'errorCss\':\'error\'});\n});\n/*]]>*/\n</script>
\n
</body>
\n
</html>\n'
"""
}