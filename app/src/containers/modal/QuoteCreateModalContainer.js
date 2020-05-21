import React, {Component} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from "redux";
import * as baseActions from 'store/modules/base';
import * as quoteActions from 'store/modules/quote';
import QuoteCreateModal from "components/user/modal/QuoteCreateModal";
import {withRouter} from "react-router-dom";


class QuoteCreateModalContainer extends Component {
    handleCancel = () => {
        const {BaseActions} = this.props;
        BaseActions.hideModal('quoteCreate');
    };

    handleCreate = async (values) => {
        const {BaseActions, QuoteActions, currentUser, location, history} = this.props;
        BaseActions.hideModal('quoteCreate');

        let folderId = location.pathname.substring(location.pathname.lastIndexOf('/') + 1);

        try {
            const response = await QuoteActions.createQuote(folderId, values);
            console.log('response', response);
            history.push(`/users/${currentUser}/quotes/folders/${folderId}`);

            //todo : 다시 로딩이 안됨
        } catch (e) {
            console.error(e);
        }


    };

    render() {
        const {visible} = this.props;

        return (
            <QuoteCreateModal visible={visible}
                              onCreate={this.handleCreate}
                              onCancel={this.handleCancel}/>
        )
    }
}

export default connect(
    (state) => ({
        visible: state.base.getIn(['modal', 'quoteCreate']),
        currentUser: state.base.getIn(['user', 'username']),
    }),
    (dispatch) => ({
        BaseActions: bindActionCreators(baseActions, dispatch),
        QuoteActions: bindActionCreators(quoteActions, dispatch)
    })
)(withRouter(QuoteCreateModalContainer));
