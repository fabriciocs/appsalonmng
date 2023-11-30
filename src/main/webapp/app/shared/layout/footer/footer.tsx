import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>
          <Translate contentKey="footer">Your Salon's Footer</Translate>
        </p>
        <div className="footer-links">
          <a href="/contact">
            <Translate contentKey="footer.contact">Contact</Translate>
          </a>
          <a href="/about">
            <Translate contentKey="footer.about">About Us</Translate>
          </a>
          // Outros links úteis
        </div>
        <div className="social-media-icons">// Ícones de redes sociais</div>
      </Col>
    </Row>
  </div>
);

export default Footer;
