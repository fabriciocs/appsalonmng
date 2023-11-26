import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/app-user">
        <Translate contentKey="global.menu.entities.appUser" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/app-service">
        <Translate contentKey="global.menu.entities.appService" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/appointment">
        <Translate contentKey="global.menu.entities.appointment" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        <Translate contentKey="global.menu.entities.product" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/financial-transaction">
        <Translate contentKey="global.menu.entities.financialTransaction" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/stock-history">
        <Translate contentKey="global.menu.entities.stockHistory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reward">
        <Translate contentKey="global.menu.entities.reward" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
