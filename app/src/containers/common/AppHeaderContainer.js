import React, {Component} from 'react';
import {connect} from 'react-redux';
import AppHeader from "../../components/common/AppHeader";
import {bindActionCreators} from "redux";
import * as baseActions from 'store/modules/base';
import {withRouter} from "react-router-dom";
import {ACCESS_TOKEN} from "../../constants";
import LoadingIndicator from "../../components/common/LoadingIndicator";

class AppHeaderContainer extends Component {
    componentDidMount() {
        this.getCurrentUser();
    }

    getCurrentUser = async () => {
        const {BaseActions} = this.props;

        try {
            const response = await BaseActions.getCurrentUser();
            console.log('response', response);
        } catch (e) {
            console.error(e);
        }
    };

    handleClickProfileMenu = async ({key}) => {
        const {BaseActions, authenticated, history} = this.props;

        if (key === 'logout' && authenticated) {
            try {
                await BaseActions.logout();
            } catch (err) {
                console.error('err', err);
            }
            localStorage.removeItem(ACCESS_TOKEN);
            history.push('/');
        }
    };

    render() {
        const {handleClickProfileMenu} = this;
        const {authenticated, currentUser, loading} = this.props;
        if (loading) {
            return <LoadingIndicator tip="Loading..."/>;
        }

        return (
            <AppHeader
                authenticated={authenticated}
                currentUser={currentUser}
                onClick={handleClickProfileMenu}
            />
        )
    }
}

export default connect(
    (state) => ({
        authenticated: state.base.get('authenticated'),
        currentUser: state.base.getIn(['user', 'username']),
        loading: state.pender.pending['base/GET_CURRENT_USER']
    }),
    (dispatch) => ({
        BaseActions: bindActionCreators(baseActions, dispatch)
    })
)(withRouter(AppHeaderContainer));
