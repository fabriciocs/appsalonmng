import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:salonmngApp/routes.dart';
import 'package:salonmngApp/shared/widgets/drawer/bloc/drawer_bloc.dart';
import 'package:flutter/material.dart';

// jhipster-merlin-needle-menu-import-entry-add

class SalonmngAppDrawer extends StatelessWidget {
   SalonmngAppDrawer({Key? key}) : super(key: key);

   static final double iconSize = 30;

  @override
  Widget build(BuildContext context) {
    return BlocListener<DrawerBloc, DrawerState>(
      listener: (context, state) {
        if(state.isLogout) {
          Navigator.popUntil(context, ModalRoute.withName(SalonmngAppRoutes.login));
          Navigator.pushNamed(context, SalonmngAppRoutes.login);
        }
      },
      child: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: <Widget>[
            header(context),
            ListTile(
              leading: Icon(Icons.home, size: iconSize,),
              title: Text('Home'),
              onTap: () => Navigator.pushNamed(context, SalonmngAppRoutes.main),
            ),
            ListTile(
              leading: Icon(Icons.settings, size: iconSize,),
              title: Text('Settings'),
              onTap: () => Navigator.pushNamed(context, SalonmngAppRoutes.settings),
            ),
            ListTile(
              leading: Icon(Icons.exit_to_app, size: iconSize,),
          title: Text('Sign out'),
              onTap: () => context.read<DrawerBloc>().add(Logout())
            ),
            Divider(thickness: 2),
            // jhipster-merlin-needle-menu-entry-add
          ],
        ),
      ),
    );
  }

  Widget header(BuildContext context) {
    return DrawerHeader(
      decoration: BoxDecoration(
        color: Theme.of(context).primaryColor,
      ),
      child: Text('Menu',
        textAlign: TextAlign.center,
        style: Theme.of(context).textTheme.headline2,
      ),
    );
  }
}
