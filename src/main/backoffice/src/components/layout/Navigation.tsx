import { Box, Button } from "@material-ui/core";
import AppBar from "@material-ui/core/AppBar";
import { createStyles, makeStyles, Theme } from "@material-ui/core/styles";
import Toolbar from "@material-ui/core/Toolbar";
import React, { FunctionComponent } from "react";
import { UserMenu } from "../auth/UserMenu";

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    menuButton: {
      marginRight: theme.spacing(2),
    },
    title: {
      flexGrow: 1,
    },
  })
);

export const Navigation: FunctionComponent = (): JSX.Element => {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <Button color="inherit" href="/">
            Beacon records
          </Button>
          <Box ml="auto">
            <UserMenu />
          </Box>
        </Toolbar>
      </AppBar>
    </div>
  );
};
