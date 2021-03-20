import {useCallback} from 'react';
import {ToastsStore} from "react-toasts";

export const useMessage = () => {
    return useCallback((text, type) => {
        if (!text){
            return;
        }
        switch (type) {
            case 'error':
                ToastsStore.error(text);
                break;
            case 'success':
                ToastsStore.success(text);
                break;
            default:
                return;
        }
    }, []);
};