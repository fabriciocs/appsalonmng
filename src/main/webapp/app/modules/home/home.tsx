import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        {/* Replace with your project's logo */}
        <span className="project-logo rounded" />
      </Col>
      <Col md="9">
        <h1 className="display-4">Welcome to YourProjectName!</h1>
        <p className="lead">This is the starting point of your application.</p>
        {account?.login ? (
          <Alert color="success">You are logged in as user {account.login}.</Alert>
        ) : (
          <Alert color="warning">
            If you want to{' '}
            <Link to="/login" className="alert-link">
              sign in
            </Link>
            , use your credentials.
            {/* Remove the default accounts information */}
          </Alert>
        )}
        {/* Add any additional information or links specific to your project */}
        <p>If you need help or have questions, check out the resources below:</p>
        <ul>
          <li>
            <Link to="/help">Help Center</Link>
          </li>
          <li>
            <Link to="/contact">Contact Support</Link>
          </li>
          {/* Add more links as needed */}
        </ul>
        <p>
          Enjoy using SalonManager? Share your experience and follow us on social media!
          {/* Update with your project's social media links */}
        </p>
      </Col>
    </Row>
  );
};

export default Home;
